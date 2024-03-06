package spring_boot_java.test_em;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring_boot_java.test_em.controllers.AuthController;
import spring_boot_java.test_em.exceptions.InsufficientFundsException;
import spring_boot_java.test_em.exceptions.RecipientNotFoundException;
import spring_boot_java.test_em.models.BankAccount;
import spring_boot_java.test_em.models.Email;
import spring_boot_java.test_em.models.PhoneNumber;
import spring_boot_java.test_em.models.User;
import spring_boot_java.test_em.payload.request.LoginRequest;
import spring_boot_java.test_em.repositories.BankAccountRepository;
import spring_boot_java.test_em.repositories.EmailRepository;
import spring_boot_java.test_em.repositories.PhoneNumberRepository;
import spring_boot_java.test_em.repositories.UserRepository;
import spring_boot_java.test_em.services.TransferService;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
class TestEMApplicationTests {

    @Autowired
    private TransferService transferService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    private AuthController authController;

    @Test
    void contextLoads() {
    }

    //Успешный трансфер денег
    @Test
    void transferMoney1() {
        User user1 = createUser("user1", "12sfd@gmail.com", "123");
        User user2 = createUser("user2", "12sfd1@gmail.com", "1234");
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(user1.getUsername());
        loginRequest.setPassword("password");

        authController.authenticateUser(loginRequest);
        try {
            transferService.transferMoney(BigDecimal.valueOf(100), user2.getId());
        } catch (InsufficientFundsException e) {
            throw new RuntimeException(e);
        }
        BankAccount bankAccount1 = (BankAccount) bankAccountRepository.findByUserId(user1.getId()).orElseThrow(() -> new RecipientNotFoundException("user1 not found"));
        BankAccount bankAccount2 = (BankAccount) bankAccountRepository.findByUserId(user2.getId()).orElseThrow(() -> new RecipientNotFoundException("user2 not found"));

        Assertions.assertEquals(900, bankAccount1.getBalance().intValue());
        Assertions.assertEquals(1100, bankAccount2.getBalance().intValue());

    }

    //Трансфер денег при недостаточных средствах
    @Test
    void transferMoney2() {
        try {
            transferService.transferMoney(BigDecimal.valueOf(1100), 2L);
        } catch (InsufficientFundsException insufficientFundsException) {
            Assertions.assertEquals("Insufficient funds for the transfer", insufficientFundsException.getMessage());
        }
        BankAccount bankAccount1 = (BankAccount) bankAccountRepository.findByUserId(1L).orElseThrow(() -> new RecipientNotFoundException("user1 not found"));
        Assertions.assertEquals(900, bankAccount1.getBalance().intValue());
    }

    //Трансфер денег несуществующему клиенту
    @Test
    void transferMoney3() {
        try {
            transferService.transferMoney(BigDecimal.valueOf(1100), 3L);
        } catch (RecipientNotFoundException recipientNotFoundException) {
            Assertions.assertEquals("Recipient not found", recipientNotFoundException.getMessage());
        } catch (InsufficientFundsException ignore) {
        }
        BankAccount bankAccount1 = (BankAccount) bankAccountRepository.findByUserId(1L).orElseThrow(() -> new RecipientNotFoundException("user1 not found"));
        Assertions.assertEquals(900, bankAccount1.getBalance().intValue());
    }

    //Трансфер денег с нулевой суммой
    @Test
    void transferMoney4() {
        transferService.transferMoney(BigDecimal.valueOf(0), 2L);
        transferService.transferMoney(BigDecimal.valueOf(0), 1L);

        BankAccount bankAccount1 = (BankAccount) bankAccountRepository.findByUserId(1L).orElseThrow(() -> new RecipientNotFoundException("user1 not found"));
        BankAccount bankAccount2 = (BankAccount) bankAccountRepository.findByUserId(2L).orElseThrow(() -> new RecipientNotFoundException("user1 not found"));
        Assertions.assertEquals(900, bankAccount1.getBalance().intValue());
        Assertions.assertEquals(1100, bankAccount2.getBalance().intValue());
    }

    //Трансфер денег с отрицательной суммой
    @Test
    void transferMoney5() {
        ResponseEntity<String> response = transferService.transferMoney(BigDecimal.valueOf(-100), 2L);

        Assertions.assertEquals("Amount should be greater than 0", response.getBody());

        BankAccount bankAccount1 = (BankAccount) bankAccountRepository.findByUserId(1L).orElseThrow(() -> new RecipientNotFoundException("user1 not found"));
        BankAccount bankAccount2 = (BankAccount) bankAccountRepository.findByUserId(2L).orElseThrow(() -> new RecipientNotFoundException("user1 not found"));
        Assertions.assertEquals(900, bankAccount1.getBalance().intValue());
        Assertions.assertEquals(1100, bankAccount2.getBalance().intValue());
    }

    private User createUser(String username, String mail, String phone) {
        User user1 = new User(username, LocalDate.now().minusYears(20),
                encoder.encode("password"));
        userRepository.save(user1);

        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setUser(user1);
        bankAccount1.setBalance(BigDecimal.valueOf(1000));
        bankAccount1.setInitialBalance(BigDecimal.valueOf(1000));
        user1.setBankAccount(bankAccount1);
        bankAccountRepository.save(bankAccount1);

        Email email1 = new Email();
        PhoneNumber phoneNumber = new PhoneNumber();
        email1.setUser(user1);
        phoneNumber.setUser(user1);
        email1.setEmail(mail);
        phoneNumber.setPhoneNumber(String.valueOf(phone));
        emailRepository.save(email1);
        phoneNumberRepository.save(phoneNumber);
        return user1;
    }

}