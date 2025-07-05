package umc.team4.domain.project.service;

import org.apache.coyote.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import umc.team4.common.response.ApiResponse;
import umc.team4.domain.project.dto.ProjectRequestDto;
import umc.team4.domain.project.dto.ProjectResponseDto;
import umc.team4.domain.project.entity.Category;

import java.util.List;

public interface ProjectService {
    ProjectResponseDto.ProjectIntroDto getProjectIntro(Long projectId);
    ProjectResponseDto.ProjectStoryDto getProjectStory(Long projectId);
    ProjectResponseDto.ProjectDetailDto getProjectDetail(Long projectId);
    ProjectResponseDto.ProjectRewardDto getProjectRewards(Long projectId);

    List<ProjectResponseDto.ProjectSummaryDto> getRandomProjects();
    ResponseEntity<ApiResponse> getListByCategory(Category category, Pageable pageable);
    List<ProjectResponseDto.ProjectSummaryDto> getDeadlineProjects();

    ProjectResponseDto.ProjectCreate createProject(ProjectRequestDto.Create requestDto);
}
