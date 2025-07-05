package umc.team4.domain.fund.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.team4.domain.fund.entity.Fund;

public interface FundRepository extends JpaRepository<Fund, Long> {
}
