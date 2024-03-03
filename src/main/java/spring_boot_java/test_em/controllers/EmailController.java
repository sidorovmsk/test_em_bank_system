package spring_boot_java.test_em.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_boot_java.test_em.services.EmailService;

import java.util.Map;

@RestController
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/add")
    public void addEmailByUserId(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        emailService.addEmailAuthenticatedUser(email);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEmailByUserId(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        return emailService.deleteEmailAuthenticatedUser(email);
    }
}
