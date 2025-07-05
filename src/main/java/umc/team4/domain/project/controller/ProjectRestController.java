package umc.team4.domain.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.team4.common.exception.GeneralException;
import umc.team4.common.response.ApiResponse;
import umc.team4.common.status.ErrorStatus;
import umc.team4.common.status.SuccessStatus;
import umc.team4.domain.project.dto.ProjectResponseDto;
import umc.team4.domain.project.entity.Category;
import umc.team4.domain.project.service.ProjectService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectRestController {

    private final ProjectService projectService;

    @GetMapping("/lists")
    public ResponseEntity<ApiResponse> getProjectsListByCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam Category category
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return projectService.getListByCategory(category, pageable);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponse> getProjectInfo(
            @PathVariable Long projectId,
            @RequestParam String type) {

        return switch (type) {
            case "detail" -> ApiResponse.onSuccess(SuccessStatus._OK, projectService.getProjectDetail(projectId));
            case "intro" -> ApiResponse.onSuccess(SuccessStatus._OK, projectService.getProjectIntro(projectId));
            case "story" -> ApiResponse.onSuccess(SuccessStatus._OK, projectService.getProjectStory(projectId));
            case "reward" -> ApiResponse.onSuccess(SuccessStatus._OK, projectService.getProjectRewards(projectId));
            default -> throw new GeneralException(ErrorStatus.INVALID_PROJECT_TYPE);
        };
    }

    @GetMapping("/best")
    public ResponseEntity<ApiResponse> getRandomProjects() {
        List<ProjectResponseDto.ProjectSummaryDto> response = projectService.getRandomProjects();
        return ApiResponse.onSuccess(SuccessStatus._OK, response);
    }

}