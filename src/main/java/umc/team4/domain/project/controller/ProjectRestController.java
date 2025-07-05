package umc.team4.domain.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.team4.common.response.ApiResponse;
import umc.team4.common.status.SuccessStatus;
import umc.team4.domain.project.dto.ProjectResponseDto;
import umc.team4.domain.project.service.ProjectService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectRestController {

    private final ProjectService projectService;

    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponse> getProjectDetail(@PathVariable Long projectId) {
        ProjectResponseDto.ProjectDetailDto response = projectService.getProjectDetail(projectId);
        return ApiResponse.onSuccess(SuccessStatus._OK, response);
    }
}