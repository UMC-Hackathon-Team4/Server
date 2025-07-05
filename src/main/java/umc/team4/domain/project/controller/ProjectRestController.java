package umc.team4.domain.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.team4.common.exception.GeneralException;
import umc.team4.common.response.ApiResponse;
import umc.team4.common.status.ErrorStatus;
import umc.team4.common.status.SuccessStatus;
import umc.team4.domain.project.dto.ProjectResponseDto;
import umc.team4.domain.project.service.ProjectService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectRestController {

    private final ProjectService projectService;

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

    @Operation(summary = "마감 임박 프로젝트 전체 조회", description = "오늘 이후 마감되는 모든 프로젝트를 마감일 오름차순으로 조회합니다.")
    @GetMapping("/deadline")
    public ResponseEntity<ApiResponse> getDeadlineProjects() {
        List<ProjectResponseDto.ProjectSummaryDto> result = projectService.getDeadlineProjects();
        return ApiResponse.onSuccess(SuccessStatus._OK, result);
    }

}