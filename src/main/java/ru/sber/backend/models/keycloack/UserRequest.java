package ru.sber.backend.models.keycloack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserRequest {
    private String username;
    private String email;
    private boolean enabled;
    private List<Credential> credentials;
    private Attributes attributes;
}