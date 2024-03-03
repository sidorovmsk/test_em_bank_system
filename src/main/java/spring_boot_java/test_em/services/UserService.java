package spring_boot_java.test_em.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_boot_java.test_em.models.User;
import spring_boot_java.test_em.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User updateUser(Long userId, User updatedUser) {
        // Проверка уникальности телефона
//        userValidationService.validatePhoneAvailability(updatedUser.getPhone(), userId);
//
//        // Проверка уникальности email
//        userValidationService.validateEmailAvailability(updatedUser.getEmail(), userId);

        // Ваши другие операции обновления пользователя

        // Сохранение или обновление пользователя
        return userRepository.save(updatedUser);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

//    public void addNewPhone(Long userId, String phone) {
//        User userInstance = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        if (userInstance != null) {
//            // Проверка наличия объекта AdditionalPhoneNumbers у пользователя
//            AdditionalPhoneNumbers additionalPhoneNumbers = userInstance.getAdditionalPhoneNumbers();
//
//            if (additionalPhoneNumbers == null) {
//                // Если объект не существует, создаем новый
//                additionalPhoneNumbers = new AdditionalPhoneNumbers();
//                additionalPhoneNumbers.setUser(userInstance);
//            }
//
//            additionalPhoneNumbers.getPhoneNumbers().add(phone);
//
//            // Сохранение или обновление объекта в базе данных
//            additionalPhoneNumbersRepository.save(additionalPhoneNumbers);
//        }
//    }
}