package catalogo.filmes.p2servidores.dto.admin;

public record AdminUserDto(
  Long id,
  String email,
  String role,
  boolean bloqueado
) {}
