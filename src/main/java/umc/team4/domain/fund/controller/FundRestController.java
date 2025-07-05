package umc.team4.domain.fund.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.team4.common.response.ApiResponse;
import umc.team4.common.status.SuccessStatus;
import umc.team4.domain.fund.dto.FundRequestDto;
import umc.team4.domain.fund.dto.FundResponseDto;
import umc.team4.domain.fund.service.FundService;
import umc.team4.domain.user.dto.UserResponseDto;

@RestController
@RequestMapping("/funds")
@RequiredArgsConstructor
public class FundRestController {

    private final FundService fundService;

    @PostMapping
    public ResponseEntity<ApiResponse> funding(
            @RequestBody FundRequestDto.fundRequestdto dto
            ) {
        FundResponseDto.fundResponseDto response = fundService.createFunding(dto);
        return ApiResponse.onSuccess(SuccessStatus._OK, response);
    }
}
