package catalogo.filmes.p2servidores.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import catalogo.filmes.p2servidores.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
