package umc.team4.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.team4.common.response.BaseResponse;
import umc.team4.common.status.ErrorStatus;
import umc.team4.common.status.SuccessStatus;
import umc.team4.domain.jwt.JwtUtil;
import umc.team4.domain.user.dto.UserResponseDto;
import umc.team4.domain.user.service.UserService;

import java.util.Map;

@Tag(name = "User API", description = "사용자 관련 기능을 담당하는 API입니다.")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Operation(
            summary = "사용자 개인정보 조회",
            security = @SecurityRequirement(name = "bearerAuth"),
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
    @ApiResponses({
            @ApiResponse(responseCode = "COMMON200", description = "SUCCESS!"),
            @ApiResponse(responseCode = "FUND4001", description = "해당 아이디의 펀드가 없습니다."),
            @ApiResponse(responseCode = "FUND4002", description = "해당 펀드의 남아있는 재고가 없습니다."),
            @ApiResponse(responseCode = "FUND4003", description = "해당 펀드를 사기 위한 유저의 코인이 부족합니다."),
            @ApiResponse(responseCode = "FUND4004", description = "해당 프로젝트에 등록된 리워드가 없습니다.")
    })
    @GetMapping("/info")
    public ResponseEntity<BaseResponse> getUserinfo(
            @RequestHeader("Authorization") String authHeader
    ) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return BaseResponse.onFailure(ErrorStatus._UNAUTHORIZED, "토큰이 없습니다.");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            return BaseResponse.onFailure(ErrorStatus._UNAUTHORIZED, "토큰이 유효하지 않습니다.");
        }

        Long userId = jwtUtil.extractUserId(token);
        UserResponseDto.userInfodto response = userService.getUserInfo(userId);
        return BaseResponse.onSuccess(SuccessStatus._OK, response);
    }

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        String token = jwtUtil.generateToken(userId);
        return ResponseEntity.ok(Map.of("token", token));
    }

}