package br.com.victorCatharina.encurtador_url.controller;

import br.com.victorCatharina.encurtador_url.dto.request.ShortUrlRequestDto;
import br.com.victorCatharina.encurtador_url.dto.response.LinkMetricsResponseDto;
import br.com.victorCatharina.encurtador_url.dto.response.ShortUrlResponseDto;
import br.com.victorCatharina.encurtador_url.service.ClickEventService;
import br.com.victorCatharina.encurtador_url.service.ShortUrlService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final ClickEventService clickEventService;

    public ShortUrlController(ShortUrlService shortUrlService,
                              ClickEventService clickEventService) {

        this.shortUrlService = shortUrlService;
        this.clickEventService = clickEventService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShortUrlResponseDto> encurtarUrl(@RequestBody ShortUrlRequestDto requestDto) {

        ShortUrlResponseDto responseDto = shortUrlService.saveShortUrl(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> redirect(@PathVariable("id") String id,
                                         HttpServletRequest request) {

        HttpHeaders headers = new HttpHeaders();
        ShortUrlResponseDto shortUrl = shortUrlService.findByID(id);
        clickEventService.saveClickEvent(id, request);

        if (shortUrl != null)
            headers.setLocation(URI.create(shortUrl.originalUrl()));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ShortUrlResponseDto>> listAll(Pageable pageable) {
        return ResponseEntity.ok(shortUrlService.listAll(pageable));
    }

    @GetMapping(path = "{shortUrlId}/metricas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LinkMetricsResponseDto> getMetrics(@PathVariable("shortUrlId") String shortUrlId) {
        return ResponseEntity.ok(clickEventService.getMetrics(shortUrlId));
    }
}
