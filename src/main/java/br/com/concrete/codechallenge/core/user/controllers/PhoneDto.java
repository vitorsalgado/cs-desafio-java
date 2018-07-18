package br.com.concrete.codechallenge.core.user.controllers;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.concrete.codechallenge.core.user.Phone;

class PhoneDto {
  @NotNull
  @Size(min = 2)
  private String ddd;
  @NotNull
  private String number;

  private PhoneDto(@NotNull @Size(min = 2) String ddd, @NotNull String number) {
    this.ddd = ddd;
    this.number = number;
  }

  PhoneDto() {

  }

  static PhoneDto mapFrom(Phone phone) {
    return new PhoneDto(phone.getDDD(), phone.getNumber());
  }

  //region Getters / Setters

  public String getDdd() {
    return ddd;
  }

  public void setDdd(String ddd) {
    this.ddd = ddd;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  //endregion
}
