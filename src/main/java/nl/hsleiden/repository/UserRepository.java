package nl.hsleiden.repository;

import nl.hsleiden.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUserById(Long userId);

    User findByEmail(String email);

    @Override
    List<User> findAll();
}
