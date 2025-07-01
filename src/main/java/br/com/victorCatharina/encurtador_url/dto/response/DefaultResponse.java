package br.com.victorCatharina.encurtador_url.dto.response;

import java.time.LocalDateTime;

public record DefaultResponse<T>(
        LocalDateTime timestamp,
        Integer statusCode,
        String developerMessage,
        String message,
        T content
) {
    public DefaultResponse(LocalDateTime timestamp, Integer statusCode) {
        this(timestamp, statusCode, null, null, null);
    }

    public DefaultResponse(LocalDateTime timestamp, Integer statusCode, String message) {
        this(timestamp, statusCode, null, message, null);
    }

    public DefaultResponse(LocalDateTime timestamp, Integer statusCode, T content) {
        this(timestamp, statusCode, null, null, content);
    }

    public DefaultResponse(LocalDateTime timestamp, Integer statusCode, String message, T content) {
        this(timestamp, statusCode, null, message, content);
    }
}
