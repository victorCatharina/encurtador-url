package br.com.victorCatharina.encurtador_url.dto.response;

import java.time.LocalDateTime;

public record ClickEventDto(
        LocalDateTime timestamp,
        String ip,
        String userAgent,
        String referrer
) {
}
