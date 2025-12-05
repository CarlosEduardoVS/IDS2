package catalogo.filmes.p2servidores.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.cors.*;
// import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

  @Bean
  public CorsConfigurationSource corsConfigurationSource(
    @Value("${app.corsAllowedOrigin}") String origin
  ) {
    CorsConfiguration cfg = new CorsConfiguration();
    cfg.addAllowedOrigin(origin);
    cfg.addAllowedHeader("*");
    cfg.addAllowedMethod("*");
    cfg.setAllowCredentials(true);

    org.springframework.web.cors.UrlBasedCorsConfigurationSource source =
      new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cfg);
    return source;
  }
}
