package ru.sber.backend.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.sber.backend.exceptions.UserNotFound;

@Service
public class JwtServiceImpl implements JwtService {
    @Override
    public String getSubClaim(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        return jwt.getClaim(claimName);
    }

    @Override
    public String getEmailClaim(Jwt jwt) {
        return jwt.getClaim("email");
    }

    @Override
    public String getPhoneNumberClaim(Jwt jwt) {
        return jwt.getClaim("phone_number");
    }

    @Override
    public String getPreferredUsernameClaim(Jwt jwt) {
        return jwt.getClaim("preferred_username");
    }

    @Override
    public String getBirthdate(Jwt jwt) {
        return jwt.getClaim("birthdate");
    }

    @Override
    public String getGender(Jwt jwt) { return jwt.getClaim("gender"); }

    public Jwt getJwtSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
            Jwt jwt = jwtAuthenticationToken.getToken();
            return jwt;
        } else {
            throw new UserNotFound("Пользователь не найден");
        }
    }
}
