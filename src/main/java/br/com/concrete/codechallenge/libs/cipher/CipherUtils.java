package br.com.concrete.codechallenge.libs.cipher;

import org.springframework.security.crypto.bcrypt.BCrypt;

import static br.com.concrete.codechallenge.libs.validators.Preconditions.checkNotNullOrEmpty;

public final class CipherUtils {
  private static final int SALT_KEY_LOG_ROUNDS = 10;

  public static String createSalt() {
    return BCrypt.gensalt(SALT_KEY_LOG_ROUNDS);
  }

  public static String createPasswordHash(String password, String salt) {
    checkNotNullOrEmpty(password, "parameter \"password\" cannot be null or empty");
    return BCrypt.hashpw(password, salt);
  }
}
