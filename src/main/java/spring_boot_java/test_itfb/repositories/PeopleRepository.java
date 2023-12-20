package spring_boot_java.test_itfb.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_boot_java.test_itfb.models.User;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);
}
