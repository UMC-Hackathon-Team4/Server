package umc.team4.domain.project.service;

import umc.team4.domain.project.dto.ProjectResponseDto;

import java.util.List;

public interface ProjectService {
    ProjectResponseDto.ProjectIntroDto getProjectIntro(Long projectId);
    ProjectResponseDto.ProjectStoryDto getProjectStory(Long projectId);
    ProjectResponseDto.ProjectDetailDto getProjectDetail(Long projectId);
    ProjectResponseDto.ProjectRewardDto getProjectRewards(Long projectId);

    List<ProjectResponseDto.ProjectSummaryDto> getRandomProjects();
    List<ProjectResponseDto.ProjectSummaryDto> getDeadlineProjects();
}
