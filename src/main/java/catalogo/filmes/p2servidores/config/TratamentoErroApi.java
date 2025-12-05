package catalogo.filmes.p2servidores.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class TratamentoErroApi {

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, Object> runtime(RuntimeException ex) {
    return Map.of("message", ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, Object> generic(Exception ex) {
    return Map.of("message", "Erro interno", "detail", ex.getClass().getName());
  }
}
