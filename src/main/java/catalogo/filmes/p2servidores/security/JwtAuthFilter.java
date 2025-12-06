package catalogo.filmes.p2servidores.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import catalogo.filmes.p2servidores.model.User;
import catalogo.filmes.p2servidores.repository.UserRepository;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserRepository userRepository;

  public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    throws ServletException, IOException {

    String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (auth == null || !auth.startsWith("Bearer ")) {
      chain.doFilter(request, response);
      return;
    }

    String token = auth.substring(7);
    try {
      Claims claims = jwtService.parse(token).getPayload();
      String email = claims.getSubject();
      String role = claims.get("role", String.class);

      User u = userRepository.findByEmail(email).orElse(null);
      if (u == null) {
        response.setStatus(401);
        response.getWriter().write("{\"message\":\"Token inválido\"}");
        return;
      }
      if (u.isBloqueado()) {
        response.setStatus(403);
        response.getWriter().write("{\"message\":\"Usuário bloqueado\"}");
        return;
      }

      var authToken = new UsernamePasswordAuthenticationToken(
        email,
        null,
        List.of(new SimpleGrantedAuthority(role))
      );
      SecurityContextHolder.getContext().setAuthentication(authToken);

      chain.doFilter(request, response);

    } catch (Exception e) {
      response.setStatus(401);
      response.getWriter().write("{\"message\":\"Token inválido\"}");
    }
  }
}