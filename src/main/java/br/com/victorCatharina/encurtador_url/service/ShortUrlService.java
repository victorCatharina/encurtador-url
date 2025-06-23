package br.com.victorCatharina.encurtador_url.service;

import br.com.victorCatharina.encurtador_url.dto.request.ShortUrlRequestDto;
import br.com.victorCatharina.encurtador_url.dto.response.ShortUrlResponseDto;
import br.com.victorCatharina.encurtador_url.entities.ShortUrlEntity;
import br.com.victorCatharina.encurtador_url.repository.ShortUrlRepository;
import br.com.victorCatharina.encurtador_url.service.mapper.ShortUrlMapper;
import br.com.victorCatharina.encurtador_url.util.RandomId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final ShortUrlMapper shortUrlMapper;

    public ShortUrlService(ShortUrlRepository shortUrlRepository,
                           ShortUrlMapper shortUrlMapper) {
        this.shortUrlRepository = shortUrlRepository;
        this.shortUrlMapper = shortUrlMapper;
    }

    public ShortUrlResponseDto saveShortUrl(ShortUrlRequestDto requestDto) {

        ShortUrlEntity shortUrl = new ShortUrlEntity();

        String generatedId = generateUniqueShortId();

        if (generatedId == null)
            throw new RuntimeException("Erro ao gerar URL encurtada");

        shortUrl.setId(generatedId);
        shortUrl.setOriginalUrl(requestDto.getUrl());
        shortUrl.setCreatedAt(Instant.now());

        shortUrlRepository.insert(shortUrl);

        return findByID(generatedId);
    }

    private String generateUniqueShortId() {
        String shortId;

        do {
            shortId = RandomId.generateRandomId();
        } while (shortUrlRepository.existsById(shortId)); // Checks MongoDB for ID collision

        return shortId;
    }

    public ShortUrlResponseDto findByID(String id) {
        return shortUrlMapper.shortUrlEntityToResponse(shortUrlRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("URL não encontrada")));
    }

    public Page<ShortUrlResponseDto> listAll(Pageable pageable) {
        return shortUrlRepository.findAll(pageable)
                .map(shortUrlMapper::shortUrlEntityToResponse);
    }
}
