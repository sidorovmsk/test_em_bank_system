package spring_boot_java.test_em.dto;

import lombok.Data;

@Data
public class PersonDto {
    public int id;
    public String username;
    public String role;
    public boolean enabled;
}
