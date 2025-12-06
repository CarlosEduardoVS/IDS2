package catalogo.filmes.p2servidores.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

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