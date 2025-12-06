package catalogo.filmes.p2servidores.dto.auth;

public record AuthResponse(
  String token,
  String email,
  String role
) {}