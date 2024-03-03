package spring_boot_java.test_em.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_boot_java.test_em.models.Email;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
