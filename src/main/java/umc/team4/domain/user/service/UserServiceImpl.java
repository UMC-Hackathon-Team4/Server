package umc.team4.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.team4.common.exception.GeneralException;
import umc.team4.common.status.ErrorStatus;
import umc.team4.domain.user.dto.UserResponseDto;
import umc.team4.domain.user.entity.User;
import umc.team4.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public UserResponseDto.userInfodto getUserInfo(Long userId) {

        // 해당 Id값의 유저가 없을 때 오류
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        return UserResponseDto.userInfodto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .coin(user.getCoin())
                .role(user.getRole())
                .disabilityType(user.getDisabilityType())
                .detail(user.getDetail())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
