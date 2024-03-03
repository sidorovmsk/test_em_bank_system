package spring_boot_java.test_em.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_boot_java.test_em.models.PhoneNumber;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
}
