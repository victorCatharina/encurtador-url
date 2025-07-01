package br.com.victorCatharina.encurtador_url.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "user_login")
public class UserEntity {
    @Id
    private String username;
    private String password;
    private String role;
    private Boolean active;
    @Field(name = "created_at")
    private LocalDateTime createdAt;

    public UserEntity(String username, String role, Boolean active, LocalDateTime createdAt) {
        this.username = username;
        this.role = role;
        this.active = active;
        this.createdAt = createdAt;
    }
}
