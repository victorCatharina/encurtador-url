package br.com.victorCatharina.encurtador_url.dto.response;

import java.util.List;

public record LinkMetricsResponseDto(
        long totalClicks,
        List<ClickEventDto> clickEvent
) {
}
