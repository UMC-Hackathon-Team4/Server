package umc.team4.domain.funding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.team4.domain.fund.entity.Fund;
import umc.team4.domain.fund.entity.Funding;

import java.util.List;

public interface FundingRepository extends JpaRepository<Funding, Long> {

    @Query("SELECT COUNT(DISTINCT f.user.userId) FROM Funding f WHERE f.fund.project.projectId = :projectId")
    Long countByProjectId(Long projectId);

    @Query("SELECT fund FROM Fund fund WHERE fund.project.projectId = :projectId")
    List<Fund> findFundsByProjectId(@Param("projectId") Long projectId);
}