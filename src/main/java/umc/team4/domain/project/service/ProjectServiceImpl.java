package umc.team4.domain.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import umc.team4.common.exception.GeneralException;
import umc.team4.common.status.ErrorStatus;
import umc.team4.domain.funding.repository.FundingRepository;
import umc.team4.domain.project.dto.ProjectResponseDto;
import umc.team4.domain.project.entity.Project;
import umc.team4.domain.project.repository.ProjectRepository;
import umc.team4.domain.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final FundingRepository fundingRepository;

    @Override
    public ProjectResponseDto.ProjectDetailDto getProjectDetail(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PROJECT_NOT_FOUND));

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
}