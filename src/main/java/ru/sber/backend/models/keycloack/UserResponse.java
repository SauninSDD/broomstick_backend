package ru.sber.backend.models.keycloack;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private UserDTO user;
}
