package br.com.victorCatharina.encurtador_url.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "short_url")
public class ShortUrlEntity {
    @Id
    private String id;
    @Field(name = "original_url")
    private String originalUrl;
    @Field(name = "created_at")
    private Instant createdAt;
    @Field(name = "expiration_date")
    private LocalDateTime expirationDate;
}
