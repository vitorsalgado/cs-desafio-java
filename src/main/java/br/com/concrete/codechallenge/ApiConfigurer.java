package br.com.concrete.codechallenge;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.concrete.codechallenge.application.security.JwtConfig;

@Component
@Scope("singleton")
public class ApiConfigurer {
  private final JwtConfig jwtConfig;

  public ApiConfigurer(JwtConfig jwtConfig) {
    this.jwtConfig = jwtConfig;
  }

  public JwtConfig getJwtConfig() {
    return jwtConfig;
  }
}
