package br.com.victorCatharina.encurtador_url.service;

import br.com.victorCatharina.encurtador_url.dto.request.ShortUrlRequestDto;
import br.com.victorCatharina.encurtador_url.dto.response.ShortUrlResponseDto;
import br.com.victorCatharina.encurtador_url.entities.ShortUrlEntity;
import br.com.victorCatharina.encurtador_url.repository.ShortUrlRepository;
import br.com.victorCatharina.encurtador_url.service.mapper.ShortUrlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ShortUrlService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int ID_LENGTH = 8; // Length of the short ID

    @Value("app.base-url")
    private String baseUrl;

    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlService(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    public ShortUrlResponseDto shortUrl(ShortUrlRequestDto requestDto) {

        ShortUrlEntity shortUrl = new ShortUrlEntity();

        String generatedId = generateUniqueShortId();

        if (generatedId == null)
            throw new RuntimeException("Erro ao gerar URL encurtada");

        shortUrl.setId(generatedId);
        shortUrl.setOriginalUrl(requestDto.getUrl());
        shortUrl.setCreatedAt(Instant.now());

        shortUrlRepository.insert(shortUrl);

        return new ShortUrlResponseDto(shortUrl.getOriginalUrl(), baseUrl + generatedId);
    }

    private String generateUniqueShortId() {
        String shortId;

        do {
            shortId = generateRandomId();
        } while (shortUrlRepository.existsById(shortId)); // Checks MongoDB for ID collision

        return shortId;
    }

    private String generateRandomId() {
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < ID_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }


    public Page<ShortUrlResponseDto> listAll(Pageable pageable) {
        return shortUrlRepository.findAll(pageable)
                .map(ShortUrlMapper::shortUrlEntityToResponse);
    }
}
