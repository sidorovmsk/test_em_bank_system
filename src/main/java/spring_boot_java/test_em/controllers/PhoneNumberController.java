package spring_boot_java.test_em.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping("/add")
    public void addPhoneNumberByUserId(@RequestBody Map<String, String> requestBody) {
        String phoneNumber = requestBody.get("phoneNumber");
        phoneNumberService.addPhoneNumberAuthenticatedUser(phoneNumber);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePhoneNumberByUserId(@RequestBody Map<String, String> requestBody) {
        String phone = requestBody.get("phoneNumber");
        return phoneNumberService.deletePhoneNumberAuthenticatedUser(phone);
    }
}
