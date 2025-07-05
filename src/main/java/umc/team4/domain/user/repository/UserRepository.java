package umc.team4.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.team4.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
