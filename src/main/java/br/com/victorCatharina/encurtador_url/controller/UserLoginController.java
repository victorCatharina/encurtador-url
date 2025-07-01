package br.com.victorCatharina.encurtador_url.controller;

import br.com.victorCatharina.encurtador_url.dto.request.RegisterUserRequestDto;
import br.com.victorCatharina.encurtador_url.dto.response.AuthLoginResponseDto;
import br.com.victorCatharina.encurtador_url.dto.response.DefaultResponse;
import br.com.victorCatharina.encurtador_url.service.UserLoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/users")
public class UserLoginController {

    private final UserLoginService userLoginService;

    public UserLoginController(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @PostMapping(path = "register", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultResponse<AuthLoginResponseDto>> register(@RequestBody @Valid RegisterUserRequestDto requestDto)
            throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new DefaultResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.CREATED.value(),
                        "Successfully registered user",
                        userLoginService.register(requestDto)
                ));
    }
}
