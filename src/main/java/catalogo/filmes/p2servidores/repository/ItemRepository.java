package catalogo.filmes.p2servidores.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import catalogo.filmes.p2servidores.model.ItemApi;
import catalogo.filmes.p2servidores.model.TipoConteudo;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemApi, Long> {
  Optional<ItemApi> findByTmdbIdAndMediaType(Long tmdbId, TipoConteudo mediaType);
}