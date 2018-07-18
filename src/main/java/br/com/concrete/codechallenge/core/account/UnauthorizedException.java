package br.com.concrete.codechallenge.core.account;

import org.springframework.http.HttpStatus;

import br.com.concrete.codechallenge.core.DomainException;

public class UnauthorizedException extends DomainException {
  public UnauthorizedException() {
    super(HttpStatus.UNAUTHORIZED, "Usuário e/ou senha inválidos");
  }

  public UnauthorizedException(String message) {
    super(HttpStatus.UNAUTHORIZED, message);
  }
}
