package catalogo.filmes.p2servidores.controller;

// import br.com.medialist.dto.list.*;
// import br.com.medialist.service.MyListService;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import catalogo.filmes.p2servidores.dto.list.AddNaListaRequest;
import catalogo.filmes.p2servidores.dto.list.ComentarioRequest;
import catalogo.filmes.p2servidores.dto.list.ListaEntidadeDto;
import catalogo.filmes.p2servidores.service.MinhaListaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/me/list")
@Tag(
    name = "Minha Lista",
    description = "Endpoints para gerenciar a lista pessoal do usuário, permitindo adicionar, remover, listar e atualizar itens."
)
public class MinhaListaController {

  private final MinhaListaService service;

  public MinhaListaController(MinhaListaService service) {
    this.service = service;
  }

  @GetMapping
  @Operation(
      summary = "Listar itens da lista do usuário",
      description = "Retorna todos os itens da lista pessoal do usuário autenticado."
  )
  public List<ListaEntidadeDto> myList(Authentication auth) {
    Long userId = SecurityUtil.userIdFromAuth(auth);
    return service.myList(userId);
  }

  @PostMapping
  @Operation(
      summary = "Adicionar item à lista",
      description = "Adiciona um novo item à lista do usuário autenticado."
  )
  public ListaEntidadeDto add(Authentication auth, @RequestBody @Valid AddNaListaRequest req) {
    Long userId = SecurityUtil.userIdFromAuth(auth);
    return service.add(userId, req);
  }

  @DeleteMapping("/{entryId}")
  @Operation(
      summary = "Remover item da lista",
      description = "Remove um item específico da lista do usuário autenticado."
  )
  public void remove(Authentication auth, @PathVariable Long entryId) {
    Long userId = SecurityUtil.userIdFromAuth(auth);
    service.remove(userId, entryId);
  }

  @PatchMapping("/{entryId}")
  @Operation(
      summary = "Atualizar item da lista",
      description = "Atualiza informações de um item da lista, como comentário ou status."
  )
  public ListaEntidadeDto update(Authentication auth, @PathVariable Long entryId,
                            @RequestBody @Valid ComentarioRequest req) {
    Long userId = SecurityUtil.userIdFromAuth(auth);
    return service.update(userId, entryId, req);
  }
}