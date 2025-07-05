package umc.team4.domain.fund.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.team4.common.exception.GeneralException;
import umc.team4.common.status.ErrorStatus;
import umc.team4.domain.fund.dto.FundRequestDto;
import umc.team4.domain.fund.dto.FundResponseDto;
import umc.team4.domain.fund.entity.Fund;
import umc.team4.domain.fund.entity.Funding;
import umc.team4.domain.fund.repository.FundRepository;
import umc.team4.domain.funding.repository.FundingRepository;
import umc.team4.domain.project.entity.Project;
import umc.team4.domain.user.entity.User;
import umc.team4.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class FundServiceImpl implements FundService {

    private final UserRepository userRepository;
    private final FundRepository fundRepository;
    private final FundingRepository fundingRepository;

    @Override
    public FundResponseDto.fundResponseDto createFunding(FundRequestDto.fundRequestdto requestDto) {

        // 해당 Id값의 유저가 없을 때 오류
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Fund fund = fundRepository.findById(requestDto.getFundId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.FUND_NOT_FOUND));

        if (fund.getStock() <= 0) {
            throw new GeneralException(ErrorStatus.FUND_STOCK_EMPTY);
        }

        if (user.getCoin() < fund.getPrice()) {
            throw new GeneralException(ErrorStatus.INSUFFICIENT_USER_COIN);
        }

        // 프로젝트 모금액 갱신
        Project project = fund.getProject();
        project.setCurrentAmount(project.getCurrentAmount() + fund.getPrice());

        // 유저 코인 갱신
        user.setCoin(user.getCoin() - fund.getPrice());

        // 재고 감소
        fund.setStock(fund.getStock() - 1);

        // funding 생성
        Funding funding = Funding.builder()
                .user(user)
                .fund(fund)
                .build();

        fundingRepository.save(funding);

        return FundResponseDto.fundResponseDto.builder()
                .userId(user.getUserId())
                .projectId(project.getProjectId())
                .fundId(fund.getFundId())
                .price(fund.getPrice())
                .projectAmount(fund.getProject().getCurrentAmount())
                .userAmount(user.getCoin())
                .build();
    }
}
