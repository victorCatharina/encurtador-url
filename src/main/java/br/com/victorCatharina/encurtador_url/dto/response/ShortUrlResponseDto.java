package br.com.victorCatharina.encurtador_url.dto.response;

public record ShortUrlResponseDto(
        String originalUrl,
        String shortUrl
) {
}
