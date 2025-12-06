package catalogo.filmes.p2servidores.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import catalogo.filmes.p2servidores.dto.auth.AuthResponse;
import catalogo.filmes.p2servidores.dto.auth.LoginRequest;
import catalogo.filmes.p2servidores.dto.auth.RegisterRequest;
import catalogo.filmes.p2servidores.model.Role;
import catalogo.filmes.p2servidores.model.User;
import catalogo.filmes.p2servidores.repository.UserRepository;
import catalogo.filmes.p2servidores.security.JwtService;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder encoder;
  private final JwtService jwtService;

  public AuthService(UserRepository userRepository, PasswordEncoder encoder, JwtService jwtService) {
    this.userRepository = userRepository;
    this.encoder = encoder;
    this.jwtService = jwtService;
  }

  public AuthResponse register(RegisterRequest req) {
    userRepository.findByEmail(req.email()).ifPresent(u -> {
      throw new RuntimeException("E-mail já cadastrado");
    });

    User u = User.builder()
      .email(req.email().toLowerCase())
      .passwordHash(encoder.encode(req.password()))
      .role(Role.ROLE_USER)
      .bloqueado(false)
      .build();

    userRepository.save(u);

    String token = jwtService.generateToken(u.getEmail(), u.getRole());
    return new AuthResponse(token, u.getEmail(), u.getRole().name());
  }

  public AuthResponse login(LoginRequest req) {
    User u = userRepository.findByEmail(req.email().toLowerCase())
      .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

    if (u.isBloqueado()) throw new RuntimeException("Usuário bloqueado");

    if (!encoder.matches(req.password(), u.getPasswordHash())) {
      throw new RuntimeException("Credenciais inválidas");
    }

    String token = jwtService.generateToken(u.getEmail(), u.getRole());
    return new AuthResponse(token, u.getEmail(), u.getRole().name());
  }
}