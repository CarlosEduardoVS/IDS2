package catalogo.filmes.p2servidores.controller;

// import br.com.medialist.dto.list.*;
// import br.com.medialist.service.MyListService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import catalogo.filmes.p2servidores.dto.list.AddNaListaRequest;
import catalogo.filmes.p2servidores.dto.list.ListaEntidadeDto;
import catalogo.filmes.p2servidores.dto.list.ComentarioRequest;
import catalogo.filmes.p2servidores.service.MinhaListaService;

import java.util.List;

@RestController
@RequestMapping("/api/me/list")
public class MinhaListaController {

  private final MinhaListaService service;

  public MinhaListaController(MinhaListaService service) {
    this.service = service;
  }

  @GetMapping
  public List<ListaEntidadeDto> myList(Authentication auth) {
    Long userId = SecurityUtil.userIdFromAuth(auth);
    return service.myList(userId);
  }

  @PostMapping
  public ListaEntidadeDto add(Authentication auth, @RequestBody @Valid AddNaListaRequest req) {
    Long userId = SecurityUtil.userIdFromAuth(auth);
    return service.add(userId, req);
  }

  @DeleteMapping("/{entryId}")
  public void remove(Authentication auth, @PathVariable Long entryId) {
    Long userId = SecurityUtil.userIdFromAuth(auth);
    service.remove(userId, entryId);
  }

  @PatchMapping("/{entryId}")
  public ListaEntidadeDto update(Authentication auth, @PathVariable Long entryId,
                            @RequestBody @Valid ComentarioRequest req) {
    Long userId = SecurityUtil.userIdFromAuth(auth);
    return service.update(userId, entryId, req);
  }
}
