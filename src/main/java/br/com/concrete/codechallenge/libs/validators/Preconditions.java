package br.com.concrete.codechallenge.libs.validators;

public final class Preconditions {
  private Preconditions() {
  }

  public static <T> T checkNotNull(T reference) {
    if (reference == null) {
      throw new NullPointerException();
    }

    return reference;
  }

  public static <T> T checkNotNull(T reference, Object errorMessage) {
    if (reference == null) {
      throw new NullPointerException(String.valueOf(errorMessage));
    }

    return reference;
  }

  public static String checkNotNullOrEmpty(String value) {
    if (value == null || value.isEmpty()) {
      throw new IllegalArgumentException();
    }

    return value;
  }

  public static String checkNotNullOrEmpty(String value, String errorMessage) {
    if (value == null || value.isEmpty()) {
      throw new IllegalArgumentException(errorMessage);
    }

    return value;
  }

  public static void checkArgument(boolean expression, Object errorMessage) {
    if (!expression) {
      throw new IllegalArgumentException(String.valueOf(errorMessage));
    }
  }
}
