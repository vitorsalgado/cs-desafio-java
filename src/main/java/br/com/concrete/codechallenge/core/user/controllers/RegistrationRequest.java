package br.com.concrete.codechallenge.core.user.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

class RegistrationRequest {
  @NotNull
  private String name;
  @NotNull
  @Email
  private String email;
  @NotNull
  private String password;

  private List<PhoneDto> phones;

  Map<String, String> phonesAsMap() {
    return phonesOrEmpty()
      .stream()
      .collect(Collectors.toMap(PhoneDto::getDdd, PhoneDto::getNumber));
  }

  private List<PhoneDto> phonesOrEmpty() {
    if (phones == null) {
      return Collections.emptyList();
    }

    return phones;
  }

  //region Getters / Setters

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<PhoneDto> getPhones() {
    return phones;
  }

  public void setPhones(List<PhoneDto> phones) {
    this.phones = phones;
  }

  //endregion
}
