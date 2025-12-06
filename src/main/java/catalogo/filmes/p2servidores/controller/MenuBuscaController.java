package catalogo.filmes.p2servidores.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import catalogo.filmes.p2servidores.dto.tmdb.PesquisaResponseDto;
import catalogo.filmes.p2servidores.service.TmdbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/tmdb")
@Tag(
    name = "Busca TMDB",
    description = "Endpoints para pesquisar filmes, séries, pessoas e outros conteúdos utilizando a API do TMDB."
)
public class MenuBuscaController {

  private final TmdbService tmdbService;

  public MenuBuscaController(TmdbService tmdbService) {
    this.tmdbService = tmdbService;
  }

  @GetMapping("/search")
  @Operation(
      summary = "Pesquisar conteúdo no TMDB",
      description = """
        Realiza uma busca na API do TMDB com base nos parâmetros informados.
        Pode pesquisar filmes, séries, pessoas ou múltiplos tipos simultaneamente.
      """
  )
  public PesquisaResponseDto search(
    @RequestParam String query,
    @RequestParam(defaultValue = "multi") String type,
    @RequestParam(defaultValue = "pt-BR") String language,
    @RequestParam(defaultValue = "1") int page
  ) {
    return tmdbService.search(type, query, language, page);
  }
}