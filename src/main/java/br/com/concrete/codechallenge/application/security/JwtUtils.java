package br.com.concrete.codechallenge.application.security;

import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import br.com.concrete.codechallenge.ApiConfigurer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
  private final ApiConfigurer apiConfigurer;

  public JwtUtils(final ApiConfigurer apiConfigurer) {
    this.apiConfigurer = apiConfigurer;
  }

  public String createJWT(final String to) {
    final Date now = new Date();
    final Calendar calendar = Calendar.getInstance();

    calendar.add(Calendar.MINUTE, 30);

    return Jwts.builder()
      .signWith(SignatureAlgorithm.HS512, key(apiConfigurer.getJwtConfig().getSecret()))
      .setSubject(to)
      .setIssuedAt(now)
      .setIssuer(apiConfigurer.getJwtConfig().getIssuer())
      .setExpiration(calendar.getTime())
      .compact();
  }

  boolean validateToken(String token) {
    return Jwts.parser()
      .requireIssuer(apiConfigurer.getJwtConfig().getIssuer())
      .setSigningKey(key(apiConfigurer.getJwtConfig().getSecret()))
      .isSigned(token);
  }

  private static String key(String plainTextSecret) {
    return Base64.getEncoder().encodeToString(plainTextSecret.getBytes());
  }
}
