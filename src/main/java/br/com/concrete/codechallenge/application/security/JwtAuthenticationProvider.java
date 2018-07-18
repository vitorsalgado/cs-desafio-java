package br.com.concrete.codechallenge.application.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import br.com.concrete.codechallenge.core.account.UnauthorizedException;

@Component
class JwtAuthenticationProvider implements AuthenticationProvider {
  private final JwtUtils jwtUtils;

  JwtAuthenticationProvider(final JwtUtils jwtUtils) {
    this.jwtUtils = jwtUtils;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    final String token = (String) authentication.getCredentials();

    if (jwtUtils.validateToken(token)) {
      authentication.setAuthenticated(true);
      return authentication;
    }

    throw new UnauthorizedException("Sessão inválida");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
  }
}
