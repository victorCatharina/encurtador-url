package br.com.victorCatharina.encurtador_url.dto.response;

import java.util.List;

public record AuthLoginResponseDto(
        String token,
        Long expiration
) {
}
