package spring_boot_java.test_em.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_boot_java.test_em.models.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
