package umc.team4.domain.fund.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FundResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class fundResponseDto {
        private Long userId;
        private Long projectId;
        private Long fundId;
        private Long price;   // 거래 코인 수
        private Long userAmount;  // 거래 후 유저 잔액
        private Long projectAmount;   // 거래 후 프로젝트 모금액
    }
}
