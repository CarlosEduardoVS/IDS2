package catalogo.filmes.p2servidores.dto.tmdb;

import java.util.List;

public record PesquisaResponseDto(
  int page,
  int totalPages,
  int totalResults,
  List<CardDto> results
) {}