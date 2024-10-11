package ru.sber.backend.entities.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String number;

    private LocalDate birthdate;

    private String gender;

    private String fullName;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
}
