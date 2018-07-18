package br.com.concrete.codechallenge.application.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
  private final String token;

  JwtAuthenticationToken(final String token) {
    super(AuthorityUtils.NO_AUTHORITIES);
    super.setAuthenticated(false);
    this.token = token;
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  @Override
  public Object getPrincipal() {
    return token;
  }
}
