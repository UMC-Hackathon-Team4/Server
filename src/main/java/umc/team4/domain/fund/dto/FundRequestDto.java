package umc.team4.domain.fund.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.team4.domain.user.entity.UserRole;

import java.time.LocalDateTime;

public class FundRequestDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class fundRequestdto {
        private Long userId;
        private Long fundId;
    }
}
