package br.com.concrete.codechallenge.core.user;

import org.springframework.http.HttpStatus;

import br.com.concrete.codechallenge.core.DomainException;
import br.com.concrete.codechallenge.core.ErrorCodes;

public class UserNotFoundException extends DomainException {
  public UserNotFoundException() {
    super(HttpStatus.BAD_REQUEST, "E-mail already exists", ErrorCodes.EMAIL_ALREADY_REGISTERED);
  }
}
