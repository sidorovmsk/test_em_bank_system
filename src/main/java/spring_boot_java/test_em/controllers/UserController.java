package spring_boot_java.test_em.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_boot_java.test_em.models.User;
import spring_boot_java.test_em.services.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestBody Map<String, Object> searchParams) {
        LocalDate birthDate = null;
        if (searchParams.containsKey("birthDate")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            birthDate = LocalDate.parse((String) searchParams.get("birthDate"), formatter);
        }

        String phone = (String) searchParams.get("phone");
        String username = (String) searchParams.get("username");
        String email = (String) searchParams.get("email");

        Pageable pageable = PageRequest.of(0, 10);

        return userService.searchUsers(birthDate, phone, username, email, pageable);
    }
}