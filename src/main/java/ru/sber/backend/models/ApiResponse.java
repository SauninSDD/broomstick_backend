package ru.sber.backend.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private T body;
    private RequestError error;

    public ApiResponse(boolean success, T body) {
        this.success = success;
        this.body = body;
    }

    public ApiResponse(boolean success) {
        this.success = success;
    }

    public ApiResponse(boolean success, RequestError error) {
        this.success = success;
        this.error = error;
    }
}
