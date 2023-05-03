package shop.mtcoding.bank.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Jpa NamedQuery (select * from user where username = ?)
    Optional<User> findByUsername(String username);
}
