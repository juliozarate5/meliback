package co.com.mercadolibre.challengefullstack.utils;

import lombok.Getter;

@Getter
public enum Messages {
  ERROR_QUERYING_TRANSACTIONS(
      "Inténtalo más tarde",
      "Inténtalo más tarde. Ha ocurrido un error al mostrar la información."),
  EXAMPLE_OTHER_BUSINESS_MESSAGE("something", "something");

  private String error;
  private String message;

  Messages(String error, String message) {
    this.error = error;
    this.message = message;
  }
}
