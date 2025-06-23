package br.com.victorCatharina.encurtador_url.service.mapper;

import br.com.victorCatharina.encurtador_url.dto.response.ShortUrlResponseDto;
import br.com.victorCatharina.encurtador_url.entities.ShortUrlEntity;
import br.com.victorCatharina.encurtador_url.service.ShortUrlBuilder;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlMapper {

    private final ShortUrlBuilder shortUrlBuilder;

    public ShortUrlMapper(ShortUrlBuilder shortUrlBuilder) {
        this.shortUrlBuilder = shortUrlBuilder;
    }

    public ShortUrlEntity shortUrlResponseToEntity(ShortUrlResponseDto responseDto) {
        return ShortUrlEntity.builder()
                .originalUrl(responseDto.originalUrl())
                .build();
    }

    public ShortUrlResponseDto shortUrlEntityToResponse(ShortUrlEntity shortUrlEntity) {
        return new ShortUrlResponseDto(shortUrlEntity.getOriginalUrl(),
                shortUrlBuilder.buildShortUrl(shortUrlEntity.getId()));
    }

}
