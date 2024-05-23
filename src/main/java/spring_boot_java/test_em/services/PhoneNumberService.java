package spring_boot_java.test_em.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import spring_boot_java.test_em.models.PhoneNumber;
import spring_boot_java.test_em.models.User;
import spring_boot_java.test_em.payload.response.MessageResponse;
import spring_boot_java.test_em.repositories.PhoneNumberRepository;
import spring_boot_java.test_em.repositories.UserRepository;

import java.util.Iterator;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> addPhoneNumberAuthenticatedUser(String phoneNumber) {
        if (phoneNumberRepository.existsByPhoneNumber(phoneNumber)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: PhoneNumber is already in use!"));
        }
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
        if (user != null) {
            PhoneNumber phoneNumberInstance = new PhoneNumber();
            phoneNumberInstance.setPhoneNumber(phoneNumber);
            phoneNumberInstance.setUser(user);
            phoneNumberRepository.save(phoneNumberInstance);
            return ResponseEntity.ok("PhoneNumber added");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: PhoneNumber was not added!"));
        }
    }

    public ResponseEntity<?> deletePhoneNumberAuthenticatedUser(String phone) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Set<PhoneNumber> phones = user.getPhones();

        if (phones.size() > 1) {
            Iterator<PhoneNumber> iterator = phones.iterator();
            while (iterator.hasNext()) {
                PhoneNumber phoneNumberInstance = iterator.next();
                if (phoneNumberInstance.getPhoneNumber().equals(phone)) {
                    iterator.remove();
                    phoneNumberRepository.delete(phoneNumberInstance);
                    return ResponseEntity.ok("Phone number deleted");
                }
            }
        } else {
            return ResponseEntity.ok("User has only one phone number");
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: PhoneNumber was not deteted!"));
    }
}
