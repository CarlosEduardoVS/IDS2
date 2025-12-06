package catalogo.filmes.p2servidores.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
  name = "itens",
  uniqueConstraints = @UniqueConstraint(columnNames = {"tmdb_id", "media_type"})
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ItemApi {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "tmdb_id", nullable = false)
  private Long tmdbId;

  @Enumerated(EnumType.STRING)
  @Column(name = "media_type", nullable = false)
  private TipoConteudo mediaType;

  @Column(name = "poster_path")
  private String posterPath;

  @Column(name = "original_language")
  private String originalLanguage;

  @Column(nullable = false)
  private String title;

  @Column(name = "original_title")
  private String originalTitle;

  private LocalDate releaseDate;

  @Column(columnDefinition = "text")
  private String overview;
}