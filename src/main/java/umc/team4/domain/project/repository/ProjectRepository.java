package umc.team4.domain.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import umc.team4.domain.project.entity.Category;
import umc.team4.domain.project.entity.Project;
import umc.team4.domain.user.entity.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p WHERE p.endDate >= CURRENT_DATE ORDER BY function('RAND')")
    List<Project> findRandomFiveProjects(Pageable pageable);

    Page<Project> findByCategory(Category category, Pageable pageable);
    List<Project> findByEndDateAfterOrderByEndDateAsc(LocalDate today);
}