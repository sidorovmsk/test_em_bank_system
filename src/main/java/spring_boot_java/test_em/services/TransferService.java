package spring_boot_java.test_em.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_boot_java.test_em.exceptions.InsufficientFundsException;
import spring_boot_java.test_em.exceptions.RecipientNotFoundException;
import spring_boot_java.test_em.models.BankAccount;
import spring_boot_java.test_em.models.User;
import spring_boot_java.test_em.repositories.BankAccountRepository;
import spring_boot_java.test_em.repositories.UserRepository;

import java.math.BigDecimal;

@Service
public class TransferService {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    public TransferService(BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<String> transferMoney(BigDecimal amount, Long recipientUserId) throws InsufficientFundsException {
        User currentUser = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);

        BankAccount senderAccount = currentUser.getBankAccount();

        if (senderAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds for the transfer");
        }

        BankAccount recipientAccount = (BankAccount) bankAccountRepository.findByUserId(recipientUserId)
                .orElseThrow(() -> new RecipientNotFoundException("Recipient not found"));

        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
        recipientAccount.setBalance(recipientAccount.getBalance().add(amount));

        bankAccountRepository.save(senderAccount);
        bankAccountRepository.save(recipientAccount);
        return ResponseEntity.ok("Transfer completed successfully");
    }
}

