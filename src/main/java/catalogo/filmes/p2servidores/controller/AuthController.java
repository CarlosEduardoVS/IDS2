package catalogo.filmes.p2servidores.controller;

// import br.com.medialist.dto.auth.*;
// import br.com.medialist.service.AuthService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.*;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import catalogo.filmes.p2servidores.dto.auth.AuthResponse;
import catalogo.filmes.p2servidores.dto.auth.LoginRequest;
import catalogo.filmes.p2servidores.dto.auth.RegisterRequest;
import catalogo.filmes.p2servidores.model.User;
import catalogo.filmes.p2servidores.repository.UserRepository;
import catalogo.filmes.p2servidores.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;
  // private final UserRepository userRepo;
  // private final PasswordEncoder encoder;
  // private final AuthenticationManager authManager;

  public AuthController(AuthService authService) {
    // UserRepository userRepo, AuthenticationManager authManager, PasswordEncoder encoder
    this.authService = authService;
    // this.userRepo = userRepo;
    // this.authManager = authManager;
    // this.encoder = encoder;
  }

  @PostMapping("/register")
  public AuthResponse register(@RequestBody @Valid RegisterRequest req) {
    return authService.register(req);
  }

  @PostMapping("/login")
  public AuthResponse login(@RequestBody @Valid LoginRequest req) {
    // valida no banco
    // User u = userRepo.findByEmail(req.email()).orElseThrow(() -> new BadCredentialsException("Credenciais inválidas"));
    // if (!encoder.matches(req.password(), u.getPasswordHash())) throw new BadCredentialsException("Credenciais inválidas");

    
    // authManager.authenticate(
    //     new UsernamePasswordAuthenticationToken(req.email(), req.password(), List.of(new SimpleGrantedAuthority(u.getRole().name())))
    // );
    
    return authService.login(req);
  }


}
