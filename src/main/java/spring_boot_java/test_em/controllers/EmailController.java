package spring_boot_java.test_em.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_boot_java.test_em.models.Email;
import spring_boot_java.test_em.services.EmailService;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/add")
    public ResponseEntity<?> addEmailByUserId(@Valid @RequestBody Email email, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request. Please check the provided data.");
        }
        return emailService.addEmailAuthenticatedUser(email.getEmail());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEmailByUserId(@Valid @RequestBody Email email, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request. Please check the provided data.");
        }
        return emailService.deleteEmailAuthenticatedUser(email.getEmail());
    }
}
