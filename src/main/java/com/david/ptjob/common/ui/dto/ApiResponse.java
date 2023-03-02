package com.david.ptjob.common.ui.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {

    private String message;
    private T body;

    private ApiResponse(String message, T body) {
        this.message = message;
        this.body = body;
    }

    public static <T> ApiResponse<T> success(T body) {
        return new ApiResponse<>("", body);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(message, null);
    }
}
