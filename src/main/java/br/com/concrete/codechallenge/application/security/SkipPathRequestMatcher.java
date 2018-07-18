package br.com.concrete.codechallenge.application.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

class SkipPathRequestMatcher implements RequestMatcher {
  private final OrRequestMatcher matchers;
  private final RequestMatcher processingMatcher;

  SkipPathRequestMatcher(final List<RequestMatcher> pathsToSkip, final String processingPath) {
    matchers = new OrRequestMatcher(pathsToSkip);
    processingMatcher = new AntPathRequestMatcher(processingPath);
  }

  @Override
  public boolean matches(HttpServletRequest request) {
    if (matchers.matches(request)) {
      return false;
    }

    return processingMatcher.matches(request);
  }
}
