package catalogo.filmes.p2servidores.service;

// import br.com.medialist.dto.tmdb.MediaCardDto;
// import br.com.medialist.dto.tmdb.SearchResponseDto;
// import br.com.medialist.model.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.annotation.JsonProperty;

import catalogo.filmes.p2servidores.dto.tmdb.CardDto;
import catalogo.filmes.p2servidores.dto.tmdb.PesquisaResponseDto;
import catalogo.filmes.p2servidores.model.TipoConteudo;

import java.time.LocalDate;
import java.util.List;

@Service
public class TmdbService {

  private final RestClient tmdb;
  private final String apiKey;
  private final String defaultLanguage;

  public TmdbService(
    @Value("${tmdb.baseUrl}") String baseUrl,
    @Value("${tmdb.apiKey}") String apiKey,
    @Value("${tmdb.defaultLanguage}") String defaultLanguage
  ) {
    this.tmdb = RestClient.builder().baseUrl(baseUrl).build();
    this.apiKey = apiKey;
    this.defaultLanguage = defaultLanguage;
  }

  public PesquisaResponseDto search(String type, String query, String language, int page) {
    String path = switch (type == null ? "" : type.toLowerCase()) {
      case "movie" -> "/search/movie";
      case "tv" -> "/search/tv";
      default -> "/search/multi";
    };

    String lang = (language == null || language.isBlank()) ? defaultLanguage : language;

    TmdbSearchResponse resp = tmdb.get()
      .uri(uri -> uri
        .path(path)
        .queryParam("api_key", apiKey)
        .queryParam("query", query)
        .queryParam("language", lang)
        .queryParam("page", Math.max(page, 1))
        .build()
      )
      .retrieve()
      .body(TmdbSearchResponse.class);

    if (resp == null) return new PesquisaResponseDto(1, 1, 0, List.of());

    List<CardDto> cards = resp.results().stream()
      .filter(r -> r.id() != null)
      .filter(r -> {
        String mt = normalizeMediaType(r.mediaType());
        return mt.equals("MOVIE") || mt.equals("TV");
      })
      .map(this::toCard)
      .toList();

    return new PesquisaResponseDto(resp.page(), resp.totalPages(), resp.totalResults(), cards);
  }

  public TmdbDetails fetchDetails(Long tmdbId, TipoConteudo mediaType, String language) {
    String lang = (language == null || language.isBlank()) ? defaultLanguage : language;

    String path = (mediaType == TipoConteudo.TV)
      ? "/tv/{id}"
      : "/movie/{id}";

    return tmdb.get()
      .uri(uri -> uri
        .path(path)
        .queryParam("api_key", apiKey)
        .queryParam("language", lang)
        .build(tmdbId)
      )
      .retrieve()
      .body(TmdbDetails.class);
  }

  private CardDto toCard(TmdbResult r) {
    String mt = normalizeMediaType(r.mediaType());
    String title = pick(r.title(), r.name());
    String originalTitle = pick(r.originalTitle(), r.originalName());
    String releaseDate = pick(r.releaseDate(), r.firstAirDate());

    return new CardDto(
      r.id(),
      mt,
      r.posterPath(),
      r.originalLanguage(),
      title,
      originalTitle,
      releaseDate,
      r.overview()
    );
  }

  private String normalizeMediaType(String mediaType) {
    if (mediaType == null) return "MOVIE";
    return switch (mediaType.toLowerCase()) {
      case "tv" -> "TV";
      case "movie" -> "MOVIE";
      default -> mediaType.toUpperCase();
    };
  }

  private String pick(String a, String b) {
    if (a != null && !a.isBlank()) return a;
    if (b != null && !b.isBlank()) return b;
    return "";
  }

  // ====== DTOs internos para Jackson ======

  public record TmdbSearchResponse(
    int page,
    @JsonProperty("total_pages") int totalPages,
    @JsonProperty("total_results") int totalResults,
    List<TmdbResult> results
  ) {}

  public record TmdbResult(
    Long id,
    @JsonProperty("media_type") String mediaType,
    @JsonProperty("poster_path") String posterPath,
    @JsonProperty("original_language") String originalLanguage,
    String title,
    @JsonProperty("original_title") String originalTitle,
    String name,
    @JsonProperty("original_name") String originalName,
    @JsonProperty("release_date") String releaseDate,
    @JsonProperty("first_air_date") String firstAirDate,
    String overview
  ) {}

  public record TmdbDetails(
    Long id,
    @JsonProperty("poster_path") String posterPath,
    @JsonProperty("original_language") String originalLanguage,
    String title,
    @JsonProperty("original_title") String originalTitle,
    String name,
    @JsonProperty("original_name") String originalName,
    @JsonProperty("release_date") String releaseDate,
    @JsonProperty("first_air_date") String firstAirDate,
    String overview
  ) {
    public String normalizedTitle() { return (title != null && !title.isBlank()) ? title : (name == null ? "" : name); }
    public String normalizedOriginalTitle() { return (originalTitle != null && !originalTitle.isBlank()) ? originalTitle : (originalName == null ? "" : originalName); }
    public LocalDate normalizedDate() {
      String d = (releaseDate != null && !releaseDate.isBlank()) ? releaseDate : firstAirDate;
      if (d == null || d.isBlank()) return null;
      try { return LocalDate.parse(d); } catch (Exception e) { return null; }
    }
  }
}
