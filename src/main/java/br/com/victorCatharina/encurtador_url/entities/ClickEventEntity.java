package br.com.victorCatharina.encurtador_url.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "click_event")
public class ClickEventEntity {

    @Field(name = "short_url_id")
    private String shortUrlId;
    private LocalDateTime timestamp;
    private String ip;
    @Field(name = "user_agent")
    private String userAgent;
    private String referrer;
}
