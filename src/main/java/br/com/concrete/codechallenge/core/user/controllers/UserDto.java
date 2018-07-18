package br.com.concrete.codechallenge.core.user.controllers;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.concrete.codechallenge.core.user.User;

public class UserDto {
  private String id;
  private String name;
  private String email;
  private String token;
  private List<PhoneDto> phones;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime lastLogin;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime created;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime modified;

  private UserDto(String id, String name, String email, LocalDateTime lastLogin, String token, List<PhoneDto> phones, LocalDateTime created, LocalDateTime modified) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.lastLogin = lastLogin;
    this.token = token;
    this.phones = phones;
    this.created = created;
    this.modified = modified;
  }

  public static UserDto mapFrom(User user) {
    return new UserDto(
      user.getId(),
      user.getName(),
      user.getEmail(),
      user.getLastLogin(),
      user.getToken(),
      user.getPhones().stream().map(PhoneDto::mapFrom).collect(Collectors.toList()),
      user.getCreated(),
      user.getModified()
    );
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public LocalDateTime getLastLogin() {
    return lastLogin;
  }

  public String getToken() {
    return token;
  }

  public List<PhoneDto> getPhones() {
    return phones;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public LocalDateTime getModified() {
    return modified;
  }
}
