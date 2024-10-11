package ru.sber.backend.models.client;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;


@Data
@AllArgsConstructor
public class AddClientCardRequest {

    private String clientCardNumber;

    private int clientCardCvc;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate clientCardExpirationDate;

    private String clientCardOwner;

}
