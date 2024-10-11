package ru.sber.backend.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.backend.entities.request.LoginRequest;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "logging")
public class Logfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String action;

    @Column(length = 6000)
    private String object;

    @Column(name = "client_id")
    private String idClient;

    public Logfile(String action, Object object, String idClient) {
        this.date = LocalDateTime.now();
        this.action = action;
        this.object = jsonMapper(object);
        this.idClient = idClient;
    }

    public Logfile(String action, String idClient) {
        this.date = LocalDateTime.now();
        this.action = action;
        this.idClient = idClient;
    }

    public Logfile(String action, Object object) {
        this.date = LocalDateTime.now();
        this.action = action;
        this.object = jsonMapper(object);
    }

    private String jsonMapper(Object object) {
        ObjectWriter ow = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writer()
                .withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(object);
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
            return object.toString();
        }
    }
}