package br.com.concrete.codechallenge.application.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.concrete.codechallenge.core.account.UnauthorizedException;

class JwtAuthorizationFilter extends AbstractAuthenticationProcessingFilter {
  private static final String AUTHORIZATION = "Authorization";
  private static final Pattern BEARER_SCHEME_PATTERN = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);

  JwtAuthorizationFilter(RequestMatcher matcher) {
    super(matcher);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    final String authorization = Optional
      .ofNullable(request.getHeader(AUTHORIZATION))
      .orElseThrow(UnauthorizedException::new);

    final String[] parts = Optional
      .of(authorization)
      .map(value -> value.split(" "))
      .orElseThrow(UnauthorizedException::new);

    if (parts.length != 2 || !BEARER_SCHEME_PATTERN.matcher(parts[0]).matches()) {
      throw new UnauthorizedException();
    }

    return getAuthenticationManager().authenticate(new JwtAuthenticationToken(parts[1]));
  }

  @Override
  protected void successfulAuthentication(
    final HttpServletRequest request,
    final HttpServletResponse response,
    final FilterChain chain,
    final Authentication authResult) throws IOException, ServletException {

    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authResult);
    SecurityContextHolder.setContext(context);
    chain.doFilter(request, response);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
    SecurityContextHolder.clearContext();
    super.unsuccessfulAuthentication(request, response, failed);
  }
}
