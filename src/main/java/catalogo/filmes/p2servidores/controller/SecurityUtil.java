package catalogo.filmes.p2servidores.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import catalogo.filmes.p2servidores.repository.UserRepository;

@Component
public class SecurityUtil {

  private static UserRepository repo;

  public SecurityUtil(UserRepository repo) {
    SecurityUtil.repo = repo;
  }

  public static Long userIdFromAuth(Authentication auth) {
    String email = auth.getName();
    return repo.findByEmail(email).orElseThrow().getId();
  }
}