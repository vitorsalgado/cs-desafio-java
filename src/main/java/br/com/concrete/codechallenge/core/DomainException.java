package br.com.concrete.codechallenge.core;

import org.springframework.http.HttpStatus;

public abstract class DomainException extends RuntimeException {
  private final HttpStatus httpStatus;
  private final String code;

  protected DomainException(final HttpStatus httpStatus, final String message) {
    this(httpStatus, message, ErrorCodes.GENERAL_ERROR);
  }

  protected DomainException(final HttpStatus httpStatus, final String message, final String code) {
    super(message);
    this.httpStatus = httpStatus;
    this.code = code;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public String getCode() {
    return code;
  }
}
