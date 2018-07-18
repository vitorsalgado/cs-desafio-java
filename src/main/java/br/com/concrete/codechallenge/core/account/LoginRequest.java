package br.com.concrete.codechallenge.core.account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

class LoginRequest {
  @NotNull
  @Email
  private String email;
  @NotNull
  private String password;

  String getEmail() {
    return email;
  }

  String getPassword() {
    return password;
  }
}
