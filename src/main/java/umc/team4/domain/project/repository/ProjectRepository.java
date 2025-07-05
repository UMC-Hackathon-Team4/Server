package umc.team4.domain.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.team4.domain.project.entity.Project;
import umc.team4.domain.user.entity.User;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}