package spring_boot_java.test_em.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import spring_boot_java.test_em.models.PhoneNumber;
import spring_boot_java.test_em.models.User;
import spring_boot_java.test_em.repositories.PhoneNumberRepository;
import spring_boot_java.test_em.repositories.UserRepository;

import java.util.Iterator;
import java.util.List;

@Service
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;
    private final UserRepository userRepository;

    public PhoneNumberService(PhoneNumberRepository phoneNumberRepository, UserRepository userRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.userRepository = userRepository;
    }

    public void addPhoneNumberByUserId(Long userId, String phoneNumber) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            PhoneNumber phoneNumberInstance = new PhoneNumber();
            phoneNumberInstance.setPhoneNumber(phoneNumber);
            phoneNumberInstance.setUser(user);
            phoneNumberRepository.save(phoneNumberInstance);
        }
    }

    public ResponseEntity<String> deletePhoneNumberByUserId(Long userId, String phone) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<PhoneNumber> phones = user.getPhones();

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
        return ResponseEntity.notFound().build();
    }
}
