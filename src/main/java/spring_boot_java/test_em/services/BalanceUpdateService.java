package spring_boot_java.test_em.services;

import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.core.DefaultLockingTaskExecutor;
import net.javacrumbs.shedlock.core.LockConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spring_boot_java.test_em.models.BankAccount;
import spring_boot_java.test_em.models.User;
import spring_boot_java.test_em.repositories.BankAccountRepository;
import spring_boot_java.test_em.repositories.UserRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class BalanceUpdateService {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final DefaultLockingTaskExecutor taskExecutor;

    @Scheduled(cron = "0 * * * * *") // каждую минуту
    public void updateBalances() {
        taskExecutor.executeWithLock(
                (Runnable) this::updateBalanceForAllUsers,
                new LockConfiguration(Instant.now(), "BalanceUpdateLock", Duration.ofMinutes(10), Duration.ofSeconds(5))
        );
    }

    private void updateBalanceForAllUsers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            updateBalance(user);
        }
    }

    private void updateBalance(User user) {
        BankAccount bankAccount = user.getBankAccount();
        BigDecimal currentBalance = bankAccount.getBalance();
        BigDecimal initialDeposit = bankAccount.getInitialBalance();

        BigDecimal increaseAmount = currentBalance.multiply(BigDecimal.valueOf(0.05));
        BigDecimal newBalance = currentBalance.add(increaseAmount);

        BigDecimal maxAllowedBalance = initialDeposit.multiply(BigDecimal.valueOf(2.07));
        if (newBalance.compareTo(maxAllowedBalance) > 0) {
            newBalance = maxAllowedBalance;
        }

        bankAccount.setBalance(newBalance);
        bankAccountRepository.save(bankAccount);
    }
}

