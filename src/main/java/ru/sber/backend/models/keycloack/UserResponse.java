package ru.sber.backend.models.keycloack;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * Клиент с ограниченной информацией
 */
@Data
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String number;
    private LocalDate birthdate;
    private String gender;

    public UserResponse(String id, String username, String email, String number, String birthdate, String gender) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.number = number;
        if(birthdate == null) {
            birthdate = "2000-01-01";
        }
        this.birthdate = LocalDate.parse(birthdate);
    }
}
