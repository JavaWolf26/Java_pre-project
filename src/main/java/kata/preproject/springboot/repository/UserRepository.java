package kata.preproject.springboot.repository;

import kata.preproject.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select distinct u from User u left join fetch u.roles where u.email = :email")
    Optional<UserDetails> findByEmail(String email);

    @Query("select distinct u from User u left join fetch u.roles")
    List<User> findAll();

    @Query("select distinct u from User u left join fetch u.roles where u.id = :id")
    User getById(Long id);
}
