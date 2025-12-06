package catalogo.filmes.p2servidores.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import catalogo.filmes.p2servidores.dto.auth.AuthResponse;
import catalogo.filmes.p2servidores.dto.auth.LoginRequest;
import catalogo.filmes.p2servidores.dto.auth.RegisterRequest;
import catalogo.filmes.p2servidores.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(
    name = "Autenticação",
    description = "Endpoints responsáveis por registro e login de usuários."
)
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  @Operation(
    summary = "Registrar novo usuário",
    description = "Cria um novo usuário comum no sistema com os dados enviados."
  )
  public AuthResponse register(@RequestBody @Valid RegisterRequest req) {
    return authService.register(req);
  }

  @PostMapping("/login")
  @Operation(
      summary = "Autenticar usuário",
      description = "Realiza login validando o email e a senha do usuário e retorna um token de autenticação."
  )
  public AuthResponse login(@RequestBody @Valid LoginRequest req) {
    return authService.login(req);
  }
}