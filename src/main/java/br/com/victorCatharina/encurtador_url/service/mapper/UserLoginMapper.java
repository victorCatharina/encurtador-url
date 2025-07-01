package br.com.victorCatharina.encurtador_url.service.mapper;

import br.com.victorCatharina.encurtador_url.dto.request.RegisterUserRequestDto;
import br.com.victorCatharina.encurtador_url.entities.UserEntity;

import java.time.LocalDateTime;

public class UserLoginMapper {
    public static UserEntity registerRequestToEntity(RegisterUserRequestDto requestDto) {
        return new UserEntity(
                requestDto.getUsername(),
                "ROLE_USER",
                true,
                LocalDateTime.now()
        );
    }
}
