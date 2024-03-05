package spring_boot_java.test_em.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_boot_java.test_em.models.PhoneNumber;
import spring_boot_java.test_em.services.PhoneNumberService;

@RestController
@RequestMapping("/phone")
public class PhoneNumberController {
    private final PhoneNumberService phoneNumberService;

    public PhoneNumberController(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPhoneNumberByUserId(@Valid @RequestBody PhoneNumber phoneNumber, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request. Please check the provided data.");
        }
        return phoneNumberService.addPhoneNumberAuthenticatedUser(phoneNumber.getPhoneNumber());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePhoneNumberByUserId(@Valid @RequestBody PhoneNumber phoneNumber, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request. Please check the provided data.");
        }
        return phoneNumberService.deletePhoneNumberAuthenticatedUser(phoneNumber.getPhoneNumber());
    }
}
