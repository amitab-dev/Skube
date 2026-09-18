package amitab.skube.service;

import amitab.skube.dto.AuthResponse;
import amitab.skube.dto.LoginRequest;
import amitab.skube.dto.RegisterRequest;
import amitab.skube.entity.User;
import amitab.skube.exception.EmailAlreadyExistsException;
import amitab.skube.exception.InvalidCredentialsException;
import amitab.skube.repository.UserRepository;
import amitab.skube.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Email already in use: " + request.email());
        }

        User user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        userRepository.save(user);

        return new AuthResponse(jwtService.generateToken(user));
    }

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        return new AuthResponse(jwtService.generateToken(user));
    }
}
