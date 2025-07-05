package umc.team4.domain.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import umc.team4.common.exception.GeneralException;
import umc.team4.common.response.ApiResponse;
import umc.team4.common.response.PageInfo;
import umc.team4.common.status.ErrorStatus;
import umc.team4.common.status.SuccessStatus;
import umc.team4.domain.fund.entity.Fund;
import umc.team4.domain.fund.repository.FundRepository;
import umc.team4.domain.funding.repository.FundingRepository;
import umc.team4.domain.project.dto.ProjectRequestDto;
import umc.team4.domain.project.dto.ProjectResponseDto;
import umc.team4.domain.project.entity.Category;
import umc.team4.domain.project.entity.Project;
import umc.team4.domain.project.repository.ProjectRepository;
import umc.team4.domain.user.entity.User;
import umc.team4.domain.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final FundingRepository fundingRepository;
    private final UserRepository userRepository;
    private final FundRepository fundRepository;

    private Project findProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PROJECT_NOT_FOUND));
    }

    @Override
    public ProjectResponseDto.ProjectIntroDto getProjectIntro(Long projectId) {
        Project project = findProject(projectId);
        return new ProjectResponseDto.ProjectIntroDto(project.getProjectId(), project.getSummary());
    }

    @Override
    public ProjectResponseDto.ProjectStoryDto getProjectStory(Long projectId) {
        Project project = findProject(projectId);
        return new ProjectResponseDto.ProjectStoryDto(project.getProjectId(), project.getStory());
    }

    @Override
    public ProjectResponseDto.ProjectDetailDto getProjectDetail(Long projectId) {
        Project project = findProject(projectId);
        User creator = project.getUser();

        double percentageDouble = project.getTargetAmount() == 0 ? 0.0 :
                (double) project.getCurrentAmount() / project.getTargetAmount() * 100;

        String percentage = String.format("%.1f%%", percentageDouble);

        return ProjectResponseDto.ProjectDetailDto.builder()
                .projectId(project.getProjectId())
                .projectTitle(project.getTitle())
                .imageUrl(project.getImageUrl())
                .category(project.getCategory())
                .summary(project.getSummary())
                .targetAmount(project.getTargetAmount())
                .currentAmount(project.getCurrentAmount())
                .creatorImageUrl(creator.getImageUrl())
                .creatorDetail(creator.getDetail())
                .creatorNickname(creator.getNickname())
                .supportersCount(project.getSupportersCount())
                .percentage(percentage)
                .build();
    }

    @Override
    public ProjectResponseDto.ProjectRewardDto getProjectRewards(Long projectId) {
        List<Fund> funds = fundingRepository.findFundsByProjectId(projectId);

        if (funds == null || funds.isEmpty()) {
            throw new GeneralException(ErrorStatus.FUND_NOT_FOUND_FOR_PROJECT);
        }

        List<ProjectResponseDto.RewardDto> rewards = funds.stream()
                .map(fund -> new ProjectResponseDto.RewardDto(
                        fund.getFundId(),
                        fund.getTitle(),
                        fund.getStock(),
                        fund.getPrice()
                ))
                .toList();

        return new ProjectResponseDto.ProjectRewardDto(projectId, rewards);
    }

    @Override
    public List<ProjectResponseDto.ProjectSummaryDto> getRandomProjects() {
        List<Project> projects = projectRepository.findRandomFiveProjects(PageRequest.of(0, 5));

        if (projects.isEmpty()) {
            throw new GeneralException(ErrorStatus.NO_PROJECTS_AVAILABLE);
        }


        return projects.stream()
                .map(project -> {
                    double percentageDouble = project.getTargetAmount() == 0 ? 0.0 :
                            (double) project.getCurrentAmount() / project.getTargetAmount() * 100;

                    String percentage = String.format("%.1f%%", percentageDouble);

                    return ProjectResponseDto.ProjectSummaryDto.builder()
                            .projectId(project.getProjectId())
                            .projectTitle(project.getTitle())
                            .imageUrl(project.getImageUrl())
                            .endDate(project.getEndDate())
                            .category(project.getCategory().name())
                            .currentAmount(project.getCurrentAmount())
                            .targetAmount(project.getTargetAmount())
                            .supportersCount(project.getSupportersCount())
                            .percentage(percentage)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ApiResponse> getListByCategory(Category category, Pageable pageable) {

        Page<Project> projectPage = projectRepository.findByCategory(category, pageable);

        PageInfo pageInfo = new PageInfo(projectPage.getNumber(), projectPage.getSize(),
                projectPage.hasNext(), projectPage.getTotalElements(), projectPage.getTotalPages());
        List<ProjectResponseDto.ProjectSummaryDto> projects = projectPage.getContent().stream()
                .map(project -> {
                    double percentageDouble = project.getTargetAmount() == 0 ? 0.0 :
                            (double) project.getCurrentAmount() / project.getTargetAmount() * 100;

                    String percentage = String.format("%.1f%%", percentageDouble);

                    return ProjectResponseDto.ProjectSummaryDto.builder()
                            .projectId(project.getProjectId())
                            .projectTitle(project.getTitle())
                            .imageUrl(project.getImageUrl())
                            .endDate(project.getEndDate())
                            .category(project.getCategory().name())
                            .currentAmount(project.getCurrentAmount())
                            .targetAmount(project.getTargetAmount())
                            .supportersCount(project.getSupportersCount())
                            .percentage(percentage)
                            .build();
                 })
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(SuccessStatus._OK, pageInfo, projects);
    }

    @Override
    public List<ProjectResponseDto.ProjectSummaryDto> getDeadlineProjects() {
        LocalDate today = LocalDate.now();
        List<Project> projects = projectRepository.findByEndDateAfterOrderByEndDateAsc(today);

        if (projects.isEmpty()) {
            throw new GeneralException(ErrorStatus.NO_PROJECTS_AVAILABLE);
        }

        return projects.stream()
                .map(p -> ProjectResponseDto.ProjectSummaryDto.builder()
                        .projectId(p.getProjectId())
                        .projectTitle(p.getTitle())
                        .imageUrl(p.getImageUrl())
                        .category(p.getCategory().name())
                        .currentAmount(p.getCurrentAmount())
                        .targetAmount(p.getTargetAmount())
                        .supportersCount(p.getSupportersCount())
                        .endDate(p.getEndDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProjectResponseDto.ProjectCreate createProject(ProjectRequestDto.Create dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        if (dto.getTargetAmount() == null || dto.getTargetAmount() <= 0) {
            throw new GeneralException(ErrorStatus.INVALID_TARGET_AMOUNT);
        }
        if (dto.getEndDate() != null && dto.getEndDate().isBefore(LocalDate.now())) {
            throw new GeneralException(ErrorStatus.PROJECT_ALREADY_CLOSED);
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null &&
                dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new GeneralException(ErrorStatus.INVALID_PROJECT_DATE_RANGE);
        }


        Project project = Project.builder()
                .user(user)
                .category(dto.getCategory())
                .targetAmount(dto.getTargetAmount())
                .currentAmount(0L)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .title(dto.getTitle())
                .summary(dto.getSummary())
                .description(dto.getDescription())
                .story(dto.getStory())
                .imageUrl(dto.getImageUrl())
                .build();

        projectRepository.save(project);

        List<Fund> funds = dto.getRewards().stream()
                .map(r -> Fund.builder()
                        .project(project)
                        .title(r.getTitle())
                        .price(r.getPrice())
                        .stock(r.getStock())
                        .build())
                .toList();

        fundRepository.saveAll(funds);

        return ProjectResponseDto.ProjectCreate.builder()
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .build();
    }

}
