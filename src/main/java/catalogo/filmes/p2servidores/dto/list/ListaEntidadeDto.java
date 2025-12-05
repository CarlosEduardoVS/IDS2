package catalogo.filmes.p2servidores.dto.list;

import java.time.Instant;

public record ListaEntidadeDto(
  Long entryId,
  Instant addedAt,
  Long tmdbId,
  String mediaType,
  String posterPath,
  String originalLanguage,
  String title,
  String originalTitle,
  String releaseDate,
  String overview,
  String comment 
) {}
