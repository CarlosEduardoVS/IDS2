package catalogo.filmes.p2servidores.service;

import org.springframework.stereotype.Service;

import catalogo.filmes.p2servidores.dto.admin.AdminUserDto;
import catalogo.filmes.p2servidores.dto.list.ListaEntidadeDto;
import catalogo.filmes.p2servidores.model.User;
import catalogo.filmes.p2servidores.repository.UserRepository;

import java.util.List;

@Service
public class AdminService {

  private final UserRepository userRepository;
  private final MinhaListaService myListService;

  public AdminService(UserRepository userRepository, MinhaListaService myListService) {
    this.userRepository = userRepository;
    this.myListService = myListService;
  }

  public List<AdminUserDto> listUsers() {
    return userRepository.findAll().stream()
      .map(u -> new AdminUserDto(u.getId(), u.getEmail(), u.getRole().name(), u.isBloqueado()))
      .toList();
  }

  public AdminUserDto setBloqueado(Long userId, boolean bloqueado) {
    User u = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    u.setBloqueado(bloqueado);
    userRepository.save(u);
    return new AdminUserDto(u.getId(), u.getEmail(), u.getRole().name(), u.isBloqueado());
  }

  public List<ListaEntidadeDto> userList(Long userId) {
    userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    return myListService.myList(userId);
  }
}