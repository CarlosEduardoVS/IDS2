package catalogo.filmes.p2servidores.controller;

// import br.com.medialist.dto.admin.*;
// import br.com.medialist.dto.list.ListEntryDto;
// import br.com.medialist.service.AdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import catalogo.filmes.p2servidores.dto.admin.AdminUserDto;
import catalogo.filmes.p2servidores.dto.admin.BlockUserRequest;
import catalogo.filmes.p2servidores.dto.list.ListaEntidadeDto;
import catalogo.filmes.p2servidores.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

  private final AdminService adminService;

  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @GetMapping("/users")
  public List<AdminUserDto> users() {
    return adminService.listUsers();
  }

  @PatchMapping("/users/{id}/block")
  public AdminUserDto block(@PathVariable Long id, @RequestBody BlockUserRequest req) {
    return adminService.setBloqueado(id, req.bloqueado());
  }

  @GetMapping("/users/{id}/list")
  public List<ListaEntidadeDto> userList(@PathVariable Long id) {
    return adminService.userList(id);
  }
}
