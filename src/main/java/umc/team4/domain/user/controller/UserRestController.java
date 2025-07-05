package umc.team4.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.team4.common.response.ApiResponse;
import umc.team4.common.status.SuccessStatus;
import umc.team4.domain.user.dto.UserResponseDto;
import umc.team4.domain.user.service.UserService;

@Tag(name = "User API", description = "사용자 관련 기능을 담당하는 API입니다.")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @Operation(
            summary = "사용자 개인정보 조회",
            description = """
        특정 사용자의 개인정보를 조회합니다.

        ✅ 요청 데이터:
        - `userId`: 조회할 사용자 ID

        ✅ 응답 데이터:
        - `userId`: 사용자 ID
        - `name`: 이름
        - `nickname`: 닉네임
        - `email`: 이메일
        - `detail`: 자기소개/설명
        - `role`: 사용자 역할 (USER, ARTIST, ADMIN)
        - `disabilityType`: 장애 유형
        - `coin`: 보유 중인 코인
        - `createdAt`: 가입 일자 (계정 생성 시각)
        """
    )
    @GetMapping("/info/{userId}")
    public ResponseEntity<ApiResponse> getUserinfo(
            @Parameter(
                    description = "조회할 사용자 ID",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId
    ) {
        UserResponseDto.userInfodto response = userService.getUserInfo(userId);
        return ApiResponse.onSuccess(SuccessStatus._OK, response);
    }
}