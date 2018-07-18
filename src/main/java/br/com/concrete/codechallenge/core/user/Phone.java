package br.com.concrete.codechallenge.core.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Phone implements Serializable {
  @Id
  @GeneratedValue
  private Long id;
  private String ddd;
  private String number;

  public Phone(final String ddd, final String number) {
    this.ddd = ddd;
    this.number = number;
  }

  Phone() {
    // Hibernate requires this
  }

  public String getDDD() {
    return ddd;
  }

  public String getNumber() {
    return number;
  }
}
