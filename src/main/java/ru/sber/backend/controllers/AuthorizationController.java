package ru.sber.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
//import ru.sber.backend.constants.GeneratePassword;
import ru.sber.backend.entities.Logfile;
import ru.sber.backend.entities.request.LoginRequest;
import ru.sber.backend.entities.request.SignupRequest;
//import ru.sber.backend.services.EmailService;
import ru.sber.backend.models.ApiResponse;
import ru.sber.backend.models.RequestError;
import ru.sber.backend.models.keycloack.*;
import ru.sber.backend.repositories.LoggingRepository;
import ru.sber.backend.services.JwtService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {

    private final String keycloakTokenUrl = "http://localhost:8080/realms/shop-realm/protocol/openid-connect/token";
    private final String keycloakCreateUserUrl = "http://localhost:8080/admin/realms/shop-realm/users";
    private final String keycloakUpdateUserUrl = "http://localhost:8080/admin/realms/shop-realm/users/";
    private final String clientId = "login-app";
    private final String grantType = "password";
    private final JwtService jwtService;
    private final LoggingRepository loggingRepository;

//    private final EmailService emailService;

    @Autowired
    public AuthorizationController(JwtService jwtService, /*, EmailService emailService*/LoggingRepository loggingRepository) {
        this.jwtService = jwtService;
//        this.emailService = emailService;
        this.loggingRepository = loggingRepository;
    }


    @PreAuthorize("hasRole('client_user')")
    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateUserInfo(@RequestBody SignupRequest signupRequest) throws JsonProcessingException {
        log.info("Выводим новые данные о клиенте {}", signupRequest);
        loggingRepository.save(new Logfile("Попытка обновления данных пользователя", signupRequest));


        Jwt jwt = jwtService.getJwtSecurityContext();

        UpdateResponse updateResponse = new UpdateResponse();

        updateResponse.setEmail(
                Optional.ofNullable(signupRequest.getEmail())
                        .orElseGet(() -> jwtService.getEmailClaim(jwt))
        );

        Attributes attributes = new Attributes();

        attributes.setPhoneNumber(
                Optional.ofNullable(signupRequest.getNumber())
                        .orElseGet(() -> jwtService.getPhoneNumberClaim(jwt))
        );

        attributes.setBirthdate(jwtService.getBirthdate(jwt));

        attributes.setGender(
                Optional.ofNullable(signupRequest.getGender())
                        .orElseGet(() -> jwtService.getGender(jwt))
        );

        updateResponse.setAttributes(attributes);

        HttpHeaders userHeaders = getHttpHeadersAdmin();
        HttpEntity<UpdateResponse> userEntity = new HttpEntity<>(updateResponse, userHeaders);

        log.info("Http entity: {}", userEntity);
        loggingRepository.save(new Logfile("Http entity", userEntity));


        try {
            ResponseEntity<String> userResponseEntity = new RestTemplate().exchange(
                    keycloakUpdateUserUrl + jwtService.getSubClaim(jwtService.getJwtSecurityContext()),
                    HttpMethod.PUT, userEntity, String.class);
            log.info("Результат отправки на keycloak: {}", userResponseEntity.getStatusCode());
            loggingRepository.save(new Logfile("Результат отправки на keycloak", userResponseEntity));

            log.info("body update: {}", userResponseEntity.getBody());

            return new ResponseEntity<>(new ApiResponse<>(true), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(false, new RequestError("Ошибка обновления данных пользователя", e.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<JsonNode>> signInUser(@RequestBody LoginRequest loginRequest) {
        loggingRepository.save(new Logfile("Попытка авторизации пользователя", loginRequest));
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", grantType);
        tokenBody.add("client_id", clientId);
        tokenBody.add("username", loginRequest.getUsername());
        tokenBody.add("password", loginRequest.getPassword());

        HttpEntity<MultiValueMap<String, String>> tokenEntity = new HttpEntity<>(tokenBody, tokenHeaders);

        try {
            ResponseEntity<String> tokenResponseEntity = new RestTemplate().exchange(
                    keycloakTokenUrl, HttpMethod.POST, tokenEntity, String.class);
            loggingRepository.save(new Logfile("Код ответа авторизации", tokenResponseEntity.getStatusCode()));

            log.info("body{}", tokenResponseEntity.getBody());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(tokenResponseEntity.getBody());

            return new ResponseEntity<>(new ApiResponse<>(true, jsonNode),
                    tokenResponseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(false, new RequestError("Ошибка авторизации пользователя", e.getMessage())), HttpStatus.OK);
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signUpUser(@RequestBody SignupRequest signupRequest) throws JsonProcessingException {
        log.info("Выводим данные о клиенте {}", signupRequest);
        loggingRepository.save(new Logfile("Попытка регистрации пользователя", signupRequest));

        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(signupRequest.getUsername());
        userRequest.setEmail(signupRequest.getEmail());
        userRequest.setEnabled(true);

        Attributes attributes = new Attributes();
        attributes.setPhoneNumber(signupRequest.getNumber());

        attributes.setBirthdate(signupRequest.getBirthdate().toString());
        attributes.setGender(signupRequest.getGender());
        userRequest.setAttributes(attributes);

        Credential credential = new Credential();
        credential.setType(grantType);
        credential.setValue(signupRequest.getPassword());

        List<Credential> credentials = new ArrayList<>();
        credentials.add(credential);
        userRequest.setCredentials(credentials);

        HttpHeaders userHeaders = getHttpHeadersAdmin();
        HttpEntity<UserRequest> userEntity = new HttpEntity<>(userRequest, userHeaders);

        log.info("Http entity: {}", userEntity);
        loggingRepository.save(new Logfile("Объект пользователя перед сохранением", userEntity));

        try {
            ResponseEntity<String> userResponseEntity = new RestTemplate().exchange(
                    keycloakCreateUserUrl, HttpMethod.POST, userEntity, String.class);
            log.info("Результат отправки на keycloak: {}", userResponseEntity.getStatusCode());
            loggingRepository.save(new Logfile("Результат отправки на keycloak ", userResponseEntity.getStatusCode()));


            return new ResponseEntity<>(new ApiResponse<>(true), userResponseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(false, new RequestError("Ошибка регистрации пользователя", e.getMessage())), HttpStatus.OK);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<JsonNode>> refreshUser(@RequestBody RefreshToken refreshToken) {
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        log.info("refreshToken: {}", refreshToken);

        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", "refresh_token");
        tokenBody.add("client_id", clientId);
        tokenBody.add("refresh_token", refreshToken.getRefresh_token());

        HttpEntity<MultiValueMap<String, String>> tokenEntity = new HttpEntity<>(tokenBody, tokenHeaders);

        try {
            ResponseEntity<String> tokenResponseEntity = new RestTemplate().exchange(
                    keycloakTokenUrl, HttpMethod.POST, tokenEntity, String.class);
            log.info("result refresh: {}", tokenResponseEntity.getStatusCode());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(tokenResponseEntity.getBody());
            return new ResponseEntity<>(new ApiResponse<>(true, jsonNode),
                    tokenResponseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(false, new RequestError("Ошибка обновления токена доступа", e.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('client_user')")
    @GetMapping
    public ResponseEntity<ApiResponse<UserResponse>> getUserDetails() {

        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setContentType(MediaType.APPLICATION_JSON);

        Jwt jwt = jwtService.getJwtSecurityContext();
        UserDTO userDetails = new UserDTO(
                jwtService.getSubClaim(jwt), jwtService.getPreferredUsernameClaim(jwt),
                jwtService.getEmailClaim(jwt), jwtService.getPhoneNumberClaim(jwt),
                jwtService.getBirthdate(jwt), jwtService.getGender(jwt)
        );

        loggingRepository.save(new Logfile("Попытка получения данных пользователя", userDetails.getId()));
        return new ResponseEntity<>(new ApiResponse<>(true, new UserResponse(userDetails)), userHeaders, HttpStatus.OK);
    }

/*    @PutMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPassword resetPassword) throws JsonProcessingException {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setType("password");
        resetPasswordRequest.setTemporary(false);
        String newPassword = GeneratePassword.generateRandomPassword(20);
        resetPasswordRequest.setValue(newPassword);
        HttpHeaders userHeaders = getHttpHeadersAdmin();
        HttpEntity<ResetPasswordRequest> resetEntity = new HttpEntity<>(resetPasswordRequest, userHeaders);
        log.info("Http entity: {}", resetEntity);
        try {
            ResponseEntity<String> userResponseEntity = new RestTemplate().exchange(
                    keycloakCreateUserUrl + "?email=" + resetPassword.getEmail(),
                    HttpMethod.GET, resetEntity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode usersNode = objectMapper.readTree(userResponseEntity.getBody());
            if (!usersNode.isArray() || usersNode.size() <= 0){
                throw new UserNotFound();
            }
            JsonNode userNode = usersNode.get(0);
            String userId = userNode.get("id").asText();
            String username = userNode.get("username").asText();

            log.info("user: {}", userResponseEntity.getBody());

            ResponseEntity<String> resetResponseEntity = new RestTemplate().exchange(
                    keycloakUpdateUserUrl +
                            userId +
                            "/reset-password",
                    HttpMethod.PUT, resetEntity, String.class);
            log.info("Результат отправки на keycloak: {}", resetResponseEntity.getStatusCode());
            resetPassword.setPassword(newPassword);
            resetPassword.setUsername(username);
            log.info("email service: {}", emailService);
            emailService.sendResetPassword(resetPassword);
            return new ResponseEntity<>(resetResponseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }*/

    private HttpHeaders getHttpHeadersAdmin() {
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setContentType(MediaType.APPLICATION_JSON);
        JsonNode rootNode = Objects.requireNonNull(signInUser(new LoginRequest("admin", "11111")).getBody()).getBody();
        String accessToken = rootNode.path("access_token").asText();
        userHeaders.setBearerAuth(accessToken);
        return userHeaders;
    }
}
