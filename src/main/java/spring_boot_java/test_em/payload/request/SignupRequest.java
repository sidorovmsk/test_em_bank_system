package spring_boot_java.test_em.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @Past()
    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(nullable = false)
    private LocalDate birthDate;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 50)
    private String phone;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private BigDecimal initialAmount;
}
