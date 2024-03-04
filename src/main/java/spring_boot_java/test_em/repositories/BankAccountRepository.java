package spring_boot_java.test_em.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_boot_java.test_em.models.BankAccount;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<Object> findByUserId(Long userId);
}
