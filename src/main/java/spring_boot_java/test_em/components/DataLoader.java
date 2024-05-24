package spring_boot_java.test_em.components;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import spring_boot_java.test_em.enums.ERole;
import spring_boot_java.test_em.models.Role;
import spring_boot_java.test_em.repositories.RoleRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        initRoles();
        initShedLockTable();
    }

    public void initRoles() {
        Role userRole = new Role();
        userRole.setName(ERole.ROLE_USER);
        Role adminRole = new Role();
        adminRole.setName(ERole.ROLE_ADMIN);
        Role modRole = new Role();
        modRole.setName(ERole.ROLE_MODERATOR);

        roleRepository.save(userRole);
        roleRepository.save(adminRole);
        roleRepository.save(modRole);
    }

    public void initShedLockTable() {
        String sql = "CREATE TABLE IF NOT EXISTS shedlock (" +
                "name VARCHAR(64), " +
                "lock_until TIMESTAMP(3) NULL, " +
                "locked_at TIMESTAMP(3) NULL, " +
                "locked_by VARCHAR(255), " +
                "PRIMARY KEY (name)" +
                ")";
        jdbcTemplate.execute(sql);
    }

}

