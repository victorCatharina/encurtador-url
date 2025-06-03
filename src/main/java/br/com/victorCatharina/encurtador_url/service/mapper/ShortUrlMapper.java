package br.com.victorCatharina.encurtador_url.service.mapper;

import br.com.victorCatharina.encurtador_url.dto.response.ShortUrlResponseDto;
import br.com.victorCatharina.encurtador_url.entities.ShortUrlEntity;

public class ShortUrlMapper {

    public static ShortUrlEntity shortUrlResponseToEntity(ShortUrlResponseDto responseDto) {
        return ShortUrlEntity.builder()
                .originalUrl(responseDto.originalUrl())
                .build();
    }

    public static ShortUrlResponseDto shortUrlEntityToResponse(ShortUrlEntity shortUrlEntity) {
        return new ShortUrlResponseDto(shortUrlEntity.getId(), shortUrlEntity.getOriginalUrl());
    }

}
