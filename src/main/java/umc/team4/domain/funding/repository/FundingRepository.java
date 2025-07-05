package umc.team4.domain.funding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.team4.domain.fund.entity.Funding;

public interface FundingRepository extends JpaRepository<Funding, Long> {

    @Query("SELECT COUNT(DISTINCT f.user.userId) FROM Funding f WHERE f.fund.project.projectId = :projectId")
    Long countByProjectId(Long projectId);
}