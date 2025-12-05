package catalogo.filmes.p2servidores.controller;

// import br.com.medialist.dto.tmdb.SearchResponseDto;
// import br.com.medialist.service.TmdbService;
import org.springframework.web.bind.annotation.*;

import catalogo.filmes.p2servidores.dto.tmdb.PesquisaResponseDto;
import catalogo.filmes.p2servidores.service.TmdbService;

@RestController
@RequestMapping("/api/tmdb")
public class MenuBuscaController {

  private final TmdbService tmdbService;

  public MenuBuscaController(TmdbService tmdbService) {
    this.tmdbService = tmdbService;
  }

  @GetMapping("/search")
  public PesquisaResponseDto search(
    @RequestParam String query,
    @RequestParam(defaultValue = "multi") String type,
    @RequestParam(defaultValue = "pt-BR") String language,
    @RequestParam(defaultValue = "1") int page
  ) {
    return tmdbService.search(type, query, language, page);
  }
}
