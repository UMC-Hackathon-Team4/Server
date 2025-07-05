package umc.team4.domain.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.team4.common.exception.GeneralException;
import umc.team4.common.status.ErrorStatus;
import umc.team4.domain.funding.repository.FundingRepository;
import umc.team4.domain.project.dto.ProjectResponseDto;
import umc.team4.domain.project.entity.Project;
import umc.team4.domain.project.repository.ProjectRepository;
import umc.team4.domain.user.entity.User;

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
}