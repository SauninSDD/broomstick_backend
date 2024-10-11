package ru.sber.backend.models.keycloack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPassword {
    private String email;
    private String username;
    private String password;
}
