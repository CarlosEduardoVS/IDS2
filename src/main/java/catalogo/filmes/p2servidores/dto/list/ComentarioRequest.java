package catalogo.filmes.p2servidores.dto.list;

import jakarta.validation.constraints.Size;

public record ComentarioRequest(
  @Size(max = 2000) String comment
) {}
