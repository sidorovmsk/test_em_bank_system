package spring_boot_java.test_em.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring_boot_java.test_em.models.BankAccount;
import spring_boot_java.test_em.models.User;
import spring_boot_java.test_em.repositories.BankAccountRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public void createBankAccountForUser(User user, BigDecimal initialAmount) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount.setBalance(initialAmount);
        bankAccount.setInitialBalance(initialAmount);
        user.setBankAccount(bankAccount);
        bankAccountRepository.save(bankAccount);
    }
}
