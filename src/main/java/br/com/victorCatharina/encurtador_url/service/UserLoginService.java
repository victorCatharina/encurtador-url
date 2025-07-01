package br.com.victorCatharina.encurtador_url.service;

import br.com.victorCatharina.encurtador_url.dto.request.RegisterUserRequestDto;
import br.com.victorCatharina.encurtador_url.dto.request.UserLoginRequestDto;
import br.com.victorCatharina.encurtador_url.dto.response.AuthLoginResponseDto;
import br.com.victorCatharina.encurtador_url.entities.UserEntity;
import br.com.victorCatharina.encurtador_url.exception.BadRequestException;
import br.com.victorCatharina.encurtador_url.repository.UserLoginRepository;
import br.com.victorCatharina.encurtador_url.security.JwtService;
import br.com.victorCatharina.encurtador_url.service.mapper.UserLoginMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {

    private final AuthenticationManager authenticationManager;
    private final UserLoginRepository userLoginRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public UserLoginService(AuthenticationManager authenticationManager,
                            UserLoginRepository userLoginRepository,
                            PasswordEncoder passwordEncoder,
                            JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userLoginRepository = userLoginRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthLoginResponseDto login(UserLoginRequestDto loginRequest) throws Exception {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails.getUsername());

        return new AuthLoginResponseDto(
                token,
                jwtService.extractExpiration(token)
        );
    }

    public AuthLoginResponseDto register(RegisterUserRequestDto requestDto) throws Exception {

        userLoginRepository.findById(requestDto.getUsername())
                .ifPresent(userEntity -> {
                    throw new BadRequestException("Username is already taken!");
                });

        UserEntity userEntity = UserLoginMapper.registerRequestToEntity(requestDto);
        String hashedPassword = passwordEncoder.encode(requestDto.getPassword());
        userEntity.setPassword(hashedPassword);

        userLoginRepository.save(userEntity);

        String token = jwtService.generateToken(userEntity.getUsername());
        return new AuthLoginResponseDto(
                token,
                jwtService.extractExpiration(token)
        );
    }
}
