package umc.team4.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.team4.domain.user.entity.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserResponseDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class userInfodto {
        private Long userId;
        private String name;
        private String nickname;
        private String email;
        private String detail;
        private UserRole role;
        private String disabilityType;
        private Long coin;
        private LocalDateTime createdAt;
    }
}
