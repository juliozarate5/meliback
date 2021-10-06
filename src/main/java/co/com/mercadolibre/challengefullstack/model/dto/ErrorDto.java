package co.com.mercadolibre.challengefullstack.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDto implements Serializable {
  private static final long serialVersionUID = 1L;

  private String error;

  private String message;

  private int status;

  private LocalDateTime date;

  /**
   * Obtiene nuevo error
   *
   * @param error String Nombre error HTTP
   * @param message String Mensaje personalizado del error HTTP
   * @param status int Codigo error HTTP
   * @return
   */
  public static ErrorDto getErrorDto(String error, String message, int status) {
    return ErrorDto.builder()
        .error(error)
        .message(message)
        .status(status)
        .date(LocalDateTime.now())
        .build();
  }
}
