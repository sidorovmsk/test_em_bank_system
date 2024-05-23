package spring_boot_java.test_em.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring_boot_java.test_em.enums.ERole;
import spring_boot_java.test_em.models.Role;
import spring_boot_java.test_em.models.User;
import spring_boot_java.test_em.repositories.RoleRepository;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public void setRolesForUser(Set<String> rolesFromRequest, User user) {
        Set<Role> roles = new HashSet<>();

        if (rolesFromRequest == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            rolesFromRequest.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
    }
}
