package br.com.concrete.codechallenge.application.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
class SecurityConfigurer extends WebSecurityConfigurerAdapter {
  private static final List<String> swaggerRoutes = Arrays.asList(
    "/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security",
    "/swagger-ui.html**", "/webjars/**");
  private static final List<String> postAllowedRoutes = Arrays.asList("/users", "/account/login");

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtAuthenticationProvider jwtAuthenticationProvider;

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(swaggerRoutes.toArray(new String[0]));
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(jwtAuthenticationProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .anonymous()

      .and()
      .authorizeRequests()
      .antMatchers("/health", "/error").permitAll()
      .antMatchers(POST, "/users", "/account/login").permitAll()
      .anyRequest().authenticated()

      .and()
      .addFilterBefore(provideFilter(), UsernamePasswordAuthenticationFilter.class)

      .csrf().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

      .and()
      .exceptionHandling().authenticationEntryPoint(new UnauthorizedEntryPoint());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public JwtAuthorizationFilter provideFilter() {
    List<RequestMatcher> matchers = postAllowedRoutes
      .stream()
      .map(path -> new AntPathRequestMatcher(path, "POST"))
      .collect(Collectors.toList());

    matchers.add(new AntPathRequestMatcher("/health", "GET"));
    matchers.add(new AntPathRequestMatcher("/error", "GET"));

    SkipPathRequestMatcher skipMatcher =
      new SkipPathRequestMatcher(matchers, "/**");

    JwtAuthorizationFilter filter =
      new JwtAuthorizationFilter(skipMatcher);

    filter.setAuthenticationManager(this.authenticationManager);

    return filter;
  }

  @Bean(name = "authenticationFilterRegistration")
  public FilterRegistrationBean filterRegistrationBean(final JwtAuthorizationFilter filter) {
    final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(filter);
    filterRegistrationBean.setEnabled(false);

    return filterRegistrationBean;
  }
}
