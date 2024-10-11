package ru.sber.backend.services.product;

import ru.sber.backend.entities.Measure;

import java.util.List;

public interface ProductMeasureService {
    Long addMeasure(Measure measure);

    Measure getMeasure(Long id);

    List<Measure> getAllMeasures();

    boolean updateMeasures(Measure measure);

    boolean deleteMeasures(Long id);
}
