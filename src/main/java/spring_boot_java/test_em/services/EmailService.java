package spring_boot_java.test_em.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import spring_boot_java.test_em.models.Email;
import spring_boot_java.test_em.models.User;
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

    public void addEmailAuthenticatedUser(String email) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
        if (user != null) {
            Email emailInstance = new Email();
            emailInstance.setEmail(email);
            emailInstance.setUser(user);
            emailRepository.save(emailInstance);
        }
    }


    public ResponseEntity<String> deleteEmailAuthenticatedUser(String email) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);

        if (user == null) {
            // Обработка случая, когда пользователь не найден
            return ResponseEntity.notFound().build();
        }

        List<Email> emails = user.getEmails();

        if (emails.size() > 1) {
            // Если у пользователя более одного email, удаляем email
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
            // Если у пользователя один email, возвращаем сообщение об ошибке
            return ResponseEntity.ok("User has only one email");
        }

        // Возвращаем, если не удалось найти email
        return ResponseEntity.notFound().build();
    }
}