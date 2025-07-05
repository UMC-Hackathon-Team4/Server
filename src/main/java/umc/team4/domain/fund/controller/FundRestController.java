package umc.team4.domain.fund.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.team4.common.response.ApiResponse;
import umc.team4.common.status.SuccessStatus;
import umc.team4.domain.fund.dto.FundRequestDto;
import umc.team4.domain.fund.dto.FundResponseDto;
import umc.team4.domain.fund.service.FundService;
import umc.team4.domain.user.dto.UserResponseDto;

@Tag(name = "Funding API", description = "프로젝트 함께하기(펀딩) 기능을 담당하는 API입니다.")
@RestController
@RequestMapping("/funds")
@RequiredArgsConstructor
public class FundRestController {

    private final FundService fundService;

    @Operation(
            summary = "프로젝트 펀딩하기",
            description = """
        사용자가 특정 프로젝트의 리워드에 펀딩을 진행합니다.

        ✅ 요청 데이터:
        - `userId`: 펀딩을 진행하는 사용자 ID
        - `fundId`: 선택한 리워드 ID

        ✅ 응답 데이터:
        - `userId`: 펀딩한 사용자 ID
        - `projectId`: 해당 리워드가 속한 프로젝트 ID
        - `fundId`: 선택한 리워드 ID
        - `price`: 리워드 가격
        - `userAmount`: 펀딩 후 유저 잔여 코인
        - `projectAmount`: 펀딩 후 프로젝트의 총 모금액
        """
    )
    @PostMapping
    public ResponseEntity<ApiResponse> funding(
            @RequestBody FundRequestDto.fundRequestdto dto
    ) {
        FundResponseDto.fundResponseDto response = fundService.createFunding(dto);
        return ApiResponse.onSuccess(SuccessStatus._OK, response);
    }
}
