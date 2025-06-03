package br.com.victorCatharina.encurtador_url.controller;

import br.com.victorCatharina.encurtador_url.dto.request.ShortUrlRequestDto;
import br.com.victorCatharina.encurtador_url.dto.response.ShortUrlResponseDto;
import br.com.victorCatharina.encurtador_url.service.ShortUrlService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("links/encurtados")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShortUrlResponseDto> encurtarUrl(@RequestBody ShortUrlRequestDto requestDto) {

        ShortUrlResponseDto responseDto = shortUrlService.shortUrl(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> redirect(@PathVariable("id") String id) {

        HttpHeaders headers = new HttpHeaders();
        ShortUrlResponseDto shortUrl = shortUrlService.findByID(id);

        if (shortUrl != null)
            headers.setLocation(URI.create(shortUrl.originalUrl()));

        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ShortUrlResponseDto>> listAll(Pageable pageable) {
        return ResponseEntity.ok(shortUrlService.listAll(pageable));
    }
}
