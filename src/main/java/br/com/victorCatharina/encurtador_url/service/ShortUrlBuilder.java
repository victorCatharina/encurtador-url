package br.com.victorCatharina.encurtador_url.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlBuilder {

    @Value("${app.base-url}")
    private String baseUrl;

    public String buildShortUrl(String id) {
        if (baseUrl == null || id == null)
            return "";

        return baseUrl.concat(id);
    }
}
