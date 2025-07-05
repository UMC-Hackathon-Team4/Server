package umc.team4.domain.fund.service;

import umc.team4.domain.fund.dto.FundRequestDto;
import umc.team4.domain.fund.dto.FundResponseDto;

public interface FundService {
    public FundResponseDto.fundResponseDto createFunding(Long userId, Long fundId);
}
