package spring_boot_java.test_em.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import spring_boot_java.test_em.models.Email;
import spring_boot_java.test_em.models.User;
import spring_boot_java.test_em.payload.response.MessageResponse;
import spring_boot_java.test_em.repositories.EmailRepository;
import spring_boot_java.test_em.repositories.UserRepository;

import java.util.Iterator;
import java.util.List;

@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final UserRepository userRepository;

    public EmailService(EmailRepository emailRepository, UserRepository userRepository) {
        this.emailRepository = emailRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> addEmailAuthenticatedUser(String email) {
        if (emailRepository.existsByEmail(email)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
        if (user != null) {
            Email emailInstance = new Email();
            emailInstance.setEmail(email);
            emailInstance.setUser(user);
            emailRepository.save(emailInstance);
            return ResponseEntity.ok("Email added");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email was not added!"));
        }
    }

    public ResponseEntity<?> deleteEmailAuthenticatedUser(String email) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<Email> emails = user.getEmails();

        if (emails.size() > 1) {
            Iterator<Email> iterator = emails.iterator();
            while (iterator.hasNext()) {
                Email emailInstance = iterator.next();
                if (emailInstance.getEmail().equals(email)) {
                    iterator.remove();
                    emailRepository.delete(emailInstance);
                    return ResponseEntity.ok("Email deleted");
                }
            }
        } else {
            return ResponseEntity.ok("User has only one email");
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Email was not deleted!"));
    }
}