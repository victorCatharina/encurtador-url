package br.com.victorCatharina.encurtador_url.security;

import br.com.victorCatharina.encurtador_url.repository.UserLoginRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserLoginRepository userLoginRepository;

    public UserDetailsServiceImpl(UserLoginRepository userLoginRepository) {
        this.userLoginRepository = userLoginRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AtomicReference<User> user = new AtomicReference<>();

        userLoginRepository.findById(username)
                .ifPresentOrElse(userEntity -> {
                    if (userEntity.getActive().equals(Boolean.TRUE))
                        user.set(new User(
                                userEntity.getUsername(),
                                userEntity.getPassword(),
                                List.of(new SimpleGrantedAuthority(userEntity.getRole()))
                        ));
                }, () -> {
                    throw new UsernameNotFoundException("User doesn't exist Or It is not active");
                });

        return user.get();
    }
}
