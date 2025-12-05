package catalogo.filmes.p2servidores.service;

// import br.com.medialist.dto.list.AddToListRequest;
// import br.com.medialist.dto.list.ListEntryDto;
// import br.com.medialist.model.*;
// import br.com.medialist.repository.ListEntryRepository;
// import br.com.medialist.repository.MediaItemRepository;
// import br.com.medialist.repository.UserRepository;
import org.springframework.stereotype.Service;

import catalogo.filmes.p2servidores.dto.list.AddNaListaRequest;
import catalogo.filmes.p2servidores.dto.list.ListaEntidadeDto;
import catalogo.filmes.p2servidores.dto.list.ComentarioRequest;
import catalogo.filmes.p2servidores.model.ListaEntidade;
import catalogo.filmes.p2servidores.model.ItemApi;
import catalogo.filmes.p2servidores.model.TipoConteudo;
import catalogo.filmes.p2servidores.model.User;
import catalogo.filmes.p2servidores.repository.ListaEntidadeRepository;
import catalogo.filmes.p2servidores.repository.ItemRepository;
import catalogo.filmes.p2servidores.repository.UserRepository;


import java.time.Instant;
import java.util.List;

@Service
public class MinhaListaService {

  private final UserRepository userRepository;
  private final ItemRepository mediaItemRepository;
  private final ListaEntidadeRepository listEntryRepository;
  private final TmdbService tmdbService;

  public MinhaListaService(
    UserRepository userRepository,
    ItemRepository mediaItemRepository,
    ListaEntidadeRepository listEntryRepository,
    TmdbService tmdbService
  ) {
    this.userRepository = userRepository;
    this.mediaItemRepository = mediaItemRepository;
    this.listEntryRepository = listEntryRepository;
    this.tmdbService = tmdbService;
  }

  public List<ListaEntidadeDto> myList(Long userId) {
    return listEntryRepository.findByUserIdOrderByAddedAtDesc(userId)
      .stream()
      .map(this::toDto)
      .toList();
  }

  public ListaEntidadeDto add(Long userId, AddNaListaRequest req) {
    User user = userRepository.findById(userId).orElseThrow();
    if (user.isBloqueado()) throw new RuntimeException("Usuário bloqueado");

    TipoConteudo mt = TipoConteudo.valueOf(req.mediaType().toUpperCase());

    // Busca detalhes no TMDB (não confia no front)
    TmdbService.TmdbDetails details = tmdbService.fetchDetails(req.tmdbId(), mt, null);
    if (details == null || details.id() == null) throw new RuntimeException("Item não encontrado no TMDB");

    ItemApi item = mediaItemRepository.findByTmdbIdAndMediaType(details.id(), mt)
      .orElseGet(() -> ItemApi.builder().tmdbId(details.id()).mediaType(mt).build());

    item.setPosterPath(details.posterPath());
    item.setOriginalLanguage(details.originalLanguage());
    item.setTitle(details.normalizedTitle());
    item.setOriginalTitle(details.normalizedOriginalTitle());
    item.setReleaseDate(details.normalizedDate());
    item.setOverview(details.overview());

    item = mediaItemRepository.save(item);

    if (listEntryRepository.existsByUserIdAndMediaItemId(userId, item.getId())) {
      throw new RuntimeException("Item já está na sua lista");
    }

    ListaEntidade entry = ListaEntidade.builder()
      .user(user)
      .mediaItem(item)
      .addedAt(Instant.now())
      .build();

    entry = listEntryRepository.save(entry);
    return toDto(entry);
  }

  public void remove(Long userId, Long entryId) {
    ListaEntidade entry = listEntryRepository.findByIdAndUserId(entryId, userId)
      .orElseThrow(() -> new RuntimeException("Item não encontrado na sua lista"));
    listEntryRepository.delete(entry);
  }

  private ListaEntidadeDto toDto(ListaEntidade e) {
    ItemApi m = e.getMediaItem();
    return new ListaEntidadeDto(
      e.getId(),
      e.getAddedAt(),
      m.getTmdbId(),
      m.getMediaType().name(),
      m.getPosterPath(),
      m.getOriginalLanguage(),
      m.getTitle(),
      m.getOriginalTitle(),
      m.getReleaseDate() == null ? "" : m.getReleaseDate().toString(),
      m.getOverview(),
      e.getComment()
    );
  }



  public ListaEntidadeDto update(Long userId, Long entryId, ComentarioRequest req) {
    ListaEntidade entry = listEntryRepository.findByIdAndUserId(entryId, userId)
      .orElseThrow(() -> new RuntimeException("Item não encontrado na sua lista"));

    entry.setComment(req.comment()); // pode ser null => “apagar comentário”
    entry = listEntryRepository.save(entry);
    return toDto(entry);
  }






}
