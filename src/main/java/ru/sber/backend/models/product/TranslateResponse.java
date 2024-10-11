package ru.sber.backend.models.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslateResponse {
    ArrayList<TranslateResponseAttributes> translations;
}
