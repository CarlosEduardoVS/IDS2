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
  private TipoConteudo mediaType; //tipoConteudo

  @Column(name = "poster_path")
  private String posterPath; //poster

  @Column(name = "original_language")
  private String originalLanguage; //linguagemOriginal

  @Column(nullable = false)
  private String title; //titulo

  @Column(name = "original_title")
  private String originalTitle; //tituloOriginal

  private LocalDate releaseDate; //data

  @Column(columnDefinition = "text")
  private String overview; //resumo
}
