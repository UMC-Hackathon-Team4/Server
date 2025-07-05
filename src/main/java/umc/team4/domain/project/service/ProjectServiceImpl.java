package umc.team4.domain.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import umc.team4.common.exception.GeneralException;
import umc.team4.common.status.ErrorStatus;
import umc.team4.domain.fund.entity.Fund;
import umc.team4.domain.fund.repository.FundRepository;
import umc.team4.domain.funding.repository.FundingRepository;
import umc.team4.domain.project.dto.ProjectResponseDto;
import umc.team4.domain.project.entity.Project;
import umc.team4.domain.project.repository.ProjectRepository;
import umc.team4.domain.user.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final FundingRepository fundingRepository;

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
        Long supportersCount = fundingRepository.countByProjectId(projectId);

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
                .supportersCount(supportersCount)
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
                        fund.getDescription(),
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
                .map(p -> ProjectResponseDto.ProjectSummaryDto.builder()
                        .projectId(p.getProjectId())
                        .projectTitle(p.getTitle())
                        .imageUrl(p.getImageUrl())
                        .category(p.getCategory().name())
                        .currentAmount(p.getCurrentAmount())
                        .targetAmount(p.getTargetAmount())
                        .endDate(p.getEndDate())
                        .build())
                .collect(Collectors.toList());
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
                        .endDate(p.getEndDate())
                        .build())
                .collect(Collectors.toList());
    }

}