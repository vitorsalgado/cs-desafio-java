package br.com.concrete.codechallenge.core.user;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.concrete.codechallenge.core.user.Phone;

public class RegisterArgs {
  private String name;
  private String email;
  private String password;
  private Map<String, String> phones;

  public RegisterArgs(String name, String email, String password, Map<String, String> phones) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.phones = phones;
  }

  String getName() {
    return name;
  }

  String getEmail() {
    return email;
  }

  String getPassword() {
    return password;
  }

  List<Phone> getPhones() {
    return phones.entrySet()
      .stream()
      .map(entry -> new Phone(entry.getKey(), entry.getValue()))
      .collect(Collectors.toList());
  }
}
