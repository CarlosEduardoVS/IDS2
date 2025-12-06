package catalogo.filmes.p2servidores.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import catalogo.filmes.p2servidores.dto.admin.AdminUserDto;
import catalogo.filmes.p2servidores.dto.admin.BlockUserRequest;
import catalogo.filmes.p2servidores.dto.list.ListaEntidadeDto;
import catalogo.filmes.p2servidores.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/admin")
@Tag(
    name = "Administração",
    description = "Endpoints exclusivos para administradores, permitindo gerenciar usuários e visualizar informações avançadas."
)
@PreAuthorize("hasRole('ADMIN')")

public class AdminController {

  private final AdminService adminService;

  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @GetMapping("/users")
  @Operation(
      summary = "Listar usuários",
      description = "Retorna uma lista com todos os usuários cadastrados no sistema."
  )
  public List<AdminUserDto> users() {
    return adminService.listUsers();
  }

  @PatchMapping("/users/{id}/block")
  @Operation(
      summary = "Bloquear ou desbloquear usuário",
      description = "Altera o status de bloqueio do usuário informado. Permite bloquear ou desbloquear com base no corpo da requisição."
  )
  public AdminUserDto block(@PathVariable Long id, @RequestBody BlockUserRequest req) {
    return adminService.setBloqueado(id, req.bloqueado());
  }

  @GetMapping("/users/{id}/list")
  @Operation(
      summary = "Listar entidades do usuário",
      description = "Mostra todas as entidades relacionadas ao usuário informado, como itens, registros ou vínculos."
  )
  public List<ListaEntidadeDto> userList(@PathVariable Long id) {
    return adminService.userList(id);
  }
}