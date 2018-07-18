package br.com.concrete.codechallenge.libs.cipher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CipherUtilsTest {
  @Test
  @DisplayName("it should generate a valid random salt key")
  void itShouldGenerateValidateSaltKey() {
    String salt = CipherUtils.createSalt();
    String salt2 = CipherUtils.createSalt();

    assertThat(salt).isNotEmpty();
    assertThat(salt2).isNotEmpty();
    assertNotEquals(salt, salt2);
  }

  @Test
  @DisplayName("it should generate a valid password hash from plain text")
  void itShouldGenerateValidPasswordHashFromPlainText() {
    String password = "ConcreteJava";
    String salt = CipherUtils.createSalt();
    String hashed = CipherUtils.createPasswordHash(password, salt);

    assertEquals(hashed, CipherUtils.createPasswordHash(password, salt));
  }
}
