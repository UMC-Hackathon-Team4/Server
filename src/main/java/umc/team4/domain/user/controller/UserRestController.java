package umc.team4.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.team4.common.response.ApiResponse;
import umc.team4.common.status.SuccessStatus;
import umc.team4.domain.user.dto.UserResponseDto;
import umc.team4.domain.user.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    // 개인정보 조회
    @GetMapping("/info/{userId}")
    public ResponseEntity<ApiResponse> getUserinfo(
            @PathVariable Long userId
    ) {
        UserResponseDto.userInfodto response = userService.getUserInfo(userId);
        return ApiResponse.onSuccess(SuccessStatus._OK, response);
    }

}
