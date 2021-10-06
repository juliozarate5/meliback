package co.com.mercadolibre.challengefullstack.utils;

import co.com.mercadolibre.challengefullstack.model.dto.ErrorDto;
import co.com.mercadolibre.challengefullstack.model.dto.ErrorResponseDTO;
import io.vavr.control.Either;
import java.util.Collections;
import org.springframework.http.HttpStatus;

public interface Utils {

  public static <T> Either<ErrorDto, T> setBusinessInfo(
      Either<ErrorDto, T> response, Messages messages) {
    return response.fold(e -> Either.left(setBusinessInfoE(e, messages)), Either::right);
  }

  public static ErrorDto setBusinessInfoE(ErrorDto error, Messages messages) {
    if (error.getMessage() == null && error.getError() == null) {
      error.setError(messages.getError());
      error.setMessage(messages.getMessage());
    }
    return error;
  }

  public static HttpStatus getStatus(ErrorDto error) {
    if (error.getError() != null) {
      return HttpStatus.valueOf(error.getError());
    }
    return HttpStatus.OK;
  }

  public static ErrorResponseDTO wrapError(ErrorDto error) {
    return ErrorResponseDTO.builder().errors(Collections.singletonList(error)).build();
  }
}
