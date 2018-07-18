package br.com.concrete.codechallenge.core.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import static br.com.concrete.codechallenge.libs.validators.Preconditions.checkNotNullOrEmpty;

@Entity
public class User implements Serializable {
  @Id
  private String id;
  private String name;
  private String email;
  private String password;
  private String salt;
  private String token;
  @OneToMany(cascade = CascadeType.ALL)
  private List<Phone> phones;
  private LocalDateTime lastLogin;
  private LocalDateTime created;
  private LocalDateTime modified;

  public User(String name, String email, String hashedPassword, String salt, String token, List<Phone> phones) {
    LocalDateTime now = LocalDateTime.now();

    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.email = email;
    this.password = hashedPassword;
    this.salt = salt;
    this.token = token;
    this.phones = phones;
    this.created = now;
    this.modified = now;
    this.lastLogin = now;
  }

  private User() {
    // Hibernate requires this
  }

  public void newLogin(String token) {
    this.token = checkNotNullOrEmpty(token);
    this.lastLogin = LocalDateTime.now();
  }

  //region Properties Getters / Setters

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getSalt() {
    return salt;
  }

  public LocalDateTime getLastLogin() {
    return lastLogin;
  }

  public String getToken() {
    return token;
  }

  public List<Phone> getPhones() {
    return phones;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public LocalDateTime getModified() {
    return modified;
  }

  //endregion
}
