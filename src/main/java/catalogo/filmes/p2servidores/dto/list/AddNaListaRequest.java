package catalogo.filmes.p2servidores.dto.list;

import jakarta.validation.constraints.NotNull;

public record AddNaListaRequest(
  @NotNull Long tmdbId,
  @NotNull String mediaType 
) {}