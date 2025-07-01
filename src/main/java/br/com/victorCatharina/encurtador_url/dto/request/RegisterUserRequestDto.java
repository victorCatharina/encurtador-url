package br.com.victorCatharina.encurtador_url.dto.request;

import br.com.victorCatharina.encurtador_url.validation.annotation.PasswordConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterUserRequestDto {

    @NotBlank(message = "Username deve ser informado!")
    private String username;
    @PasswordConstraint
    private String password;
}
