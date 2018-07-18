package br.com.concrete.codechallenge.application.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

import br.com.concrete.codechallenge.core.DomainException;

@RestControllerAdvice
class GlobalExceptionHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(DomainException.class)
  ResponseEntity<ErrorDto> handleDomainException(HttpServletResponse response, DomainException ex) {
    LOGGER.error(ex.getMessage(), ex);
    return new ResponseEntity<>(new ErrorDto(ex.getMessage()), ex.getHttpStatus());
  }

  @ExceptionHandler({Exception.class, AuthenticationException.class})
  ResponseEntity<ErrorDto> handleUncaught(HttpServletResponse response, Exception ex) {
    LOGGER.error(ex.getMessage(), ex);
    return new ResponseEntity<>(new ErrorDto(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
