package umc.team4.domain.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.team4.common.exception.GeneralException;
import umc.team4.common.response.BaseResponse;
import umc.team4.common.status.ErrorStatus;
import umc.team4.common.status.SuccessStatus;
import umc.team4.domain.jwt.JwtUtil;
import umc.team4.domain.project.dto.ProjectRequestDto;
import umc.team4.domain.project.dto.ProjectResponseDto;
import umc.team4.domain.project.entity.Category;
import umc.team4.domain.project.service.ProjectService;

import java.util.List;

@Tag(name = "Project API", description = "프로젝트 등록, 조회 기능을 담당하는 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectRestController {

    private final JwtUtil jwtUtil;
    private final ProjectService projectService;

    @Operation(
            summary = "카테고리별 프로젝트 목록 전체 조회",
            description = """
    선택한 카테고리를 기준으로 해당하는 프로젝트들을 페이지네이션 형태로 조회합니다.

    ✅ 요청 데이터:
    - `page`: 현재 페이지 번호 (기본값: 0)
    - `size`: 한 페이지당 결과 수 (기본값: 20)
    - `category`: 조회할 프로젝트 카테고리 (`PUBLISHING`, `ART`, `GOODS` 중 하나)

    ✅ 응답 데이터:
    - `pageInfo`:
        - `page`: 현재 페이지 번호
        - `size`: 요청한 페이지 크기
        - `hasNext`: 다음 페이지 존재 여부
        - `totalElements`: 전체 프로젝트 수
        - `totalPages`: 전체 페이지 수
    - `result` (List):
        - `projectId`: 프로젝트 ID
        - `projectTitle`: 프로젝트 제목
        - `imageUrl`: 대표 이미지 URL
        - `category`: 프로젝트 카테고리
        - `currentAmount`: 현재 모금액
        - `targetAmount`: 목표 모금액
        - `percentage`: 달성률
        - `supportersCount`: 후원자 수
        - `endDate`: 프로젝트 종료일 (yyyy-MM-dd)
    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "COMMON200", description = "SUCCESS!"),
            @ApiResponse(responseCode = "PROJECT4001", description = "존재하지 않는 프로젝트입니다."),
    })
    @GetMapping("/lists")
    public ResponseEntity<BaseResponse> getProjectsListByCategory(
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0", required = false)
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "페이지 크기", example = "20", required = false)
            @RequestParam(defaultValue = "20") int size,

            @Parameter(description = "조회할 프로젝트 카테고리 (PUBLISHING / ART / GOODS)", example = "GOODS", required = true)
            @RequestParam Category category
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return projectService.getListByCategory(category, pageable);
    }

    @Operation(
            summary = "프로젝트 상세 조회",
            description = """
    프로젝트 ID에 따라 상세 정보를 조회합니다.

    ✅ 요청 데이터:
    - `projectId`: 조회할 프로젝트 ID (PathVariable)
    - `type`: 조회 유형 (쿼리 파라미터)
        - `detail`: 상세 정보 (제목, 요약, 이미지, 카테고리, 목표금액, 현재금액, 달성률 등 포함)
        - `intro`: 소개 조회
        - `story`: 스토리 조회
        - `reward`: 리워드 정보 조회

    ✅ 응답 데이터:

    ◼️ type=detail:
    - `projectId`: 프로젝트 ID
    - `projectTitle`: 제목
    - `imageUrl`: 이미지 URL
    - `category`: 카테고리 (GOODS, ART, PUBLISHING)
    - `summary`: 요약 설명
    - `targetAmount`: 목표 금액
    - `currentAmount`: 현재 모금액
    - `percentage`: 달성률
    - `creatorImageUrl`: 작가 이미지
    - `creatorNickname`: 작가 닉네임
    - `creatorDetail`: 작가 소개
    - `supportersCount`: 후원자 수

    ◼️ type=intro:
    - `projectId`: 프로젝트 ID
    - `summary`: 소개

    ◼️ type=story:
    - `projectId`: 프로젝트 ID
    - `story`: 스토리

    ◼️ type=reward:
    - `projectId`: 프로젝트 ID
    - `rewards` (List):
        - `fundId`: 리워드 ID
        - `title`: 리워드 제목
        - `description`: 리워드 설명
        - `price`: 가격
    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "COMMON200", description = "SUCCESS!"),
            @ApiResponse(responseCode = "PROJECT4001", description = "존재하지 않는 프로젝트입니다."),
            @ApiResponse(responseCode = "PROJECT4003", description = "유효하지 않은 type입니다. [detail, intro, story, reward] 중 하나여야 합니다."),
    })
    @GetMapping("/{projectId}")
    public ResponseEntity<BaseResponse> getProjectInfo(
            @Parameter(description = "조회할 프로젝트 ID", example = "1", required = true)
            @PathVariable Long projectId,

            @Parameter(description = "조회 유형 (detail, intro, story, reward 중 하나)", example = "detail", required = true)
            @RequestParam String type) {

        return switch (type) {
            case "detail" -> BaseResponse.onSuccess(SuccessStatus._OK, projectService.getProjectDetail(projectId));
            case "intro" -> BaseResponse.onSuccess(SuccessStatus._OK, projectService.getProjectIntro(projectId));
            case "story" -> BaseResponse.onSuccess(SuccessStatus._OK, projectService.getProjectStory(projectId));
            case "reward" -> BaseResponse.onSuccess(SuccessStatus._OK, projectService.getProjectRewards(projectId));
            default -> throw new GeneralException(ErrorStatus.INVALID_PROJECT_TYPE);
        };
    }

    @Operation(
            summary = "프로젝트 전체 조회",
            description = """
    진행 중인 프로젝트들 중 무작위로 5개를 반환합니다.

    ✅ 요청 데이터:
    - 없음

    ✅ 응답 데이터:
    `result`: 무작위로 추출된 프로젝트 리스트 (최대 5개)
    - `projectId`: 프로젝트 ID
    - `projectTitle`: 프로젝트 제목
    - `imageUrl`: 대표 이미지 URL
    - `category`: 프로젝트 카테고리 (PUBLISHING / ART / GOODS)
    - `currentAmount`: 현재 모금액
    - `targetAmount`: 목표 금액
    - `supportersCount`: 후원자 수
    - `percentage`: 달성률
    - `endDate`: 프로젝트 마감일
    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "COMMON200", description = "SUCCESS!"),
            @ApiResponse(responseCode = "PROJECT4001", description = "존재하지 않는 프로젝트입니다."),
            @ApiResponse(responseCode = "PROJECT4002", description = "추천할 프로젝트가 없습니다."),
    })
    @GetMapping("/best")
    public ResponseEntity<BaseResponse> getRandomProjects() {
        List<ProjectResponseDto.ProjectSummaryDto> response = projectService.getRandomProjects();
        return BaseResponse.onSuccess(SuccessStatus._OK, response);
    }

    @Operation(
            summary = "마감 임박 프로젝트 전체 조회",
            description = """
    오늘 이후 마감 예정인 모든 프로젝트를 마감일 기준 오름차순으로 조회합니다.

    ✅ 요청 데이터:
    - 없음

    ✅ 응답 데이터:
    `result`: 마감일이 임박한 프로젝트 리스트
    - `projectId`: 프로젝트 ID
    - `projectTitle`: 프로젝트 제목
    - `imageUrl`: 대표 이미지 URL
    - `category`: 프로젝트 카테고리 (PUBLISHING / ART / GOODS)
    - `currentAmount`: 현재 모금액
    - `targetAmount`: 목표 금액
    - `supportersCount`: 후원자 수
    - `percentage`: 달성률
    - `endDate`: 마감일
    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "COMMON200", description = "SUCCESS!"),
            @ApiResponse(responseCode = "PROJECT4001", description = "존재하지 않는 프로젝트입니다."),
            @ApiResponse(responseCode = "PROJECT4005", description = "이미 마감된 프로젝트입니다."),
    })
    @GetMapping("/deadline")
    public ResponseEntity<BaseResponse> getDeadlineProjects() {
        List<ProjectResponseDto.ProjectSummaryDto> result = projectService.getDeadlineProjects();
        return BaseResponse.onSuccess(SuccessStatus._OK, result);
    }

    @PostMapping
    @Operation(
            summary = "프로젝트 등록하기",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = """
    프로젝트 정보, 스토리 작성, 리워드 설정을 포함하여 새로운 프로젝트를 등록합니다.

    ✅ 요청 데이터:

    🔹 프로젝트 정보:
    - `userId`: 프로젝트를 등록하는 사용자 ID
    - `category`: 프로젝트 카테고리 (PUBLISHING / ART / GOODS)
    - `targetAmount`: 목표 금액
    - `startDate`: 프로젝트 시작일
    - `endDate`: 프로젝트 마감일

    🔹 스토리 작성:
    - `title`: 프로젝트 제목
    - `summary`: 프로젝트 요약
    - `description`: 프로젝트 소개
    - `story`: 프로젝트 스토리
    - `imageUrl`: 대표 이미지 URL

    🔹 리워드 만들기 (`rewards`): 복수 개 입력 가능
    - `title`: 리워드 제목
    - `stock`: 판매 수량
    - `price`: 리워드 가격

    ✅ 응답 데이터:
    - `projectId`: 생성된 프로젝트 ID
    - `title`: 등록한 프로젝트 제목
    - `startDate`: 프로젝트 시작일
    - `endDate`: 프로젝트 마감일
    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "COMMON200", description = "SUCCESS!"),
            @ApiResponse(responseCode = "PROJECT5001", description = "프로젝트 등록에 실패했습니다."),
            @ApiResponse(responseCode = "PROJECT4006", description = "프로젝트 시작일은 종료일 이전이어야 합니다."),
    })
    public ResponseEntity<BaseResponse> createProject(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ProjectRequestDto.Create dto) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return BaseResponse.onFailure(ErrorStatus._UNAUTHORIZED, "토큰이 없습니다.");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            return BaseResponse.onFailure(ErrorStatus._UNAUTHORIZED, "토큰이 유효하지 않습니다.");
        }

        Long userId = jwtUtil.extractUserId(token);

        ProjectResponseDto.ProjectCreate response = projectService.createProject(dto, userId);
        return BaseResponse.onSuccess(SuccessStatus._OK, response);
    }

}
