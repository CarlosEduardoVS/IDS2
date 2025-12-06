package catalogo.filmes.p2servidores.dto.tmdb;

public record CardDto(
  Long tmdbId,
  String mediaType,         
  String posterPath,
  String originalLanguage,
  String title,
  String originalTitle,
  String releaseDate,       
  String overview
) {}