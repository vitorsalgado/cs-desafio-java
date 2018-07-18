package br.com.concrete.codechallenge.application.error;

public class ErrorDto {
  private final String message;

  public ErrorDto(final String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
