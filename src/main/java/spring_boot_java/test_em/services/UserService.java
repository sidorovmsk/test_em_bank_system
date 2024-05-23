package spring_boot_java.test_em.services;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring_boot_java.test_em.models.Email;
import spring_boot_java.test_em.models.PhoneNumber;
import spring_boot_java.test_em.models.User;
import spring_boot_java.test_em.payload.request.SignupRequest;
import spring_boot_java.test_em.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PhoneNumberService phoneNumberService;
    private final BankAccountService bankAccountService;
    private final PasswordEncoder encoder;
    private final RoleService roleService;

    public List<User> searchUsers(LocalDate birthDate, String phone, String username, String email, Pageable pageable) {
        Specification<User> spec = Specification.where(null);

        if (birthDate != null) {
            spec = spec.and((root, query, builder) ->
                    builder.greaterThanOrEqualTo(root.get("birthDate"), birthDate));
        }

        if (phone != null && !phone.isEmpty()) {
            spec = spec.and((root, query, builder) -> {
                Join<User, PhoneNumber> phoneJoin = root.join("phones", JoinType.LEFT);
                return builder.equal(phoneJoin.get("phoneNumber"), phone);
            });
        }

        if (username != null && !username.isEmpty()) {
            spec = spec.and((root, query, builder) ->
                    builder.like(builder.lower(root.get("username")), username.toLowerCase() + "%"));
        }

        if (email != null && !email.isEmpty()) {
            spec = spec.and((root, query, builder) -> {
                Join<User, Email> emailJoin = root.join("emails", JoinType.LEFT);
                return builder.equal(emailJoin.get("email"), email);
            });
        }

        List<User> users;
        if (pageable != null) {
            Page<User> userPage = userRepository.findAll(spec, pageable);
            users = userPage.getContent();
        } else {
            users = userRepository.findAll(spec);
        }

        return users;
    }

    public void registerUser(SignupRequest signUpRequest) {
        validateSignUpRequest(signUpRequest);

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getBirthDate(),
                encoder.encode(signUpRequest.getPassword()));

        roleService.setRolesForUser(signUpRequest.getRole(), user);

        userRepository.save(user);

        bankAccountService.createBankAccountForUser(user, signUpRequest.getInitialAmount());
        emailService.createEmailForUser(user, signUpRequest.getEmail());
        phoneNumberService.createPhoneNumberForUser(user, signUpRequest.getPhone());
    }

    private void validateSignUpRequest(SignupRequest signUpRequest) {
        validateUsername(signUpRequest.getUsername());
        emailService.validateEmail(signUpRequest.getEmail());
        phoneNumberService.validatePhoneNumber(signUpRequest.getPhone());
    }

    private void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Error: Username is already taken!");
        }
    }
}