package catalogo.filmes.p2servidores.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import catalogo.filmes.p2servidores.model.ListaEntidade;

import java.util.List;
import java.util.Optional;

public interface ListaEntidadeRepository extends JpaRepository<ListaEntidade, Long> {
  List<ListaEntidade> findByUserIdOrderByAddedAtDesc(Long userId);
  Optional<ListaEntidade> findByIdAndUserId(Long id, Long userId);
  boolean existsByUserIdAndMediaItemId(Long userId, Long mediaItemId);
}