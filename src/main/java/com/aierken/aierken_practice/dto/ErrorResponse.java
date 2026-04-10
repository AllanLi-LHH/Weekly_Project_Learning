package com.aierken.aierken_practice.dto;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
    }
}
