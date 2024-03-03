package spring_boot_java.test_em.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_boot_java.test_em.models.User;
import spring_boot_java.test_em.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        User result = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(result);
    }

//    @PostMapping("/add/phone/{userId}")
//    public ResponseEntity<?> addNewPhone(@PathVariable Long userId, @RequestBody Phone phone) {
//        userService.addNewPhone(userId, phone.getNumber());
//        return ResponseEntity.ok("awd");
//    }
}