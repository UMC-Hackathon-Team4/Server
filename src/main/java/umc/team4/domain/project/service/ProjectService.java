package umc.team4.domain.project.service;

import umc.team4.domain.project.dto.ProjectResponseDto;

import java.util.List;

public interface ProjectService {
    ProjectResponseDto.ProjectDetailDto getProjectDetail(Long projectId);

    List<ProjectResponseDto.ProjectSummaryDto> getRandomProjects();
}
