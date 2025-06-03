package br.com.victorCatharina.encurtador_url.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShortUrlRequestDto {

    private String url;
}
