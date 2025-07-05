package umc.team4.domain.user.service;

import umc.team4.domain.user.dto.UserResponseDto;

public interface UserService {
    UserResponseDto.userInfodto getUserInfo(Long memberId);
}
