package br.com.concrete.codechallenge.application.error;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Configuration
class GlobalErrorAttributes {
  @Bean
  public ErrorAttributes errorAttributes() {
    return new DefaultErrorAttributes() {
      @Override
      public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        final Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        final Map<String, Object> error = new HashMap<>();

        error.put("message", errorAttributes.get("message"));

        return error;
      }
    };
  }
}
