package catalogo.filmes.p2servidores.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
  name = "lista_entidades",
  uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "media_item_id"})
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ListaEntidade {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "media_item_id", nullable = false)
  private ItemApi mediaItem;

  @Column(nullable = false)
  private Instant addedAt;

  @Column(columnDefinition = "text")
  private String comment;
}