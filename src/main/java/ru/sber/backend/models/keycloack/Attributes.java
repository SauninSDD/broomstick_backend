package ru.sber.backend.models.keycloack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Attributes {
    private String phoneNumber;
    private String birthdate;
    private String gender;
    private String fullName;
}