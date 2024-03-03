package spring_boot_java.test_em.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_boot_java.test_em.services.PhoneNumberService;

import java.util.Map;

@RestController
@RequestMapping("/phone")
public class PhoneNumberController {
    private final PhoneNumberService phoneNumberService;

    public PhoneNumberController(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @PostMapping("/add/{userId}")
    public void addPhoneNumberByUserId(@PathVariable Long userId, @RequestBody Map<String, String> requestBody) {
        String phoneNumber = requestBody.get("phoneNumber");
        phoneNumberService.addPhoneNumberByUserId(userId, phoneNumber);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deletePhoneNumberByUserId(@PathVariable Long userId, @RequestBody Map<String, String> requestBody) {
        String phone = requestBody.get("phoneNumber");
        return phoneNumberService.deletePhoneNumberByUserId(userId, phone);
    }
}
