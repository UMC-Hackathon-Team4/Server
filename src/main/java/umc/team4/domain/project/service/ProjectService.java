package umc.team4.domain.project.service;

import umc.team4.domain.project.dto.ProjectResponseDto;

public interface ProjectService {
    ProjectResponseDto.ProjectDetailDto getProjectDetail(Long projectId);
}
