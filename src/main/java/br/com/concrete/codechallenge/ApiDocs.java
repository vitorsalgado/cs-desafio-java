package br.com.concrete.codechallenge;

import com.google.common.base.Predicates;

import com.fasterxml.classmate.TypeResolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
class ApiDocs {
  private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
  private final TypeResolver typeResolver;

  ApiDocs(TypeResolver typeResolver) {
    this.typeResolver = typeResolver;
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .consumes(Collections.singleton(CONTENT_TYPE_APPLICATION_JSON))
      .produces(Collections.singleton(CONTENT_TYPE_APPLICATION_JSON))
      .tags(
        new Tag("account", "Account"),
        new Tag("users", "User"),
        new Tag("health", "Health"))
      .genericModelSubstitutes(ResponseEntity.class)
      .alternateTypeRules(
        newRule(typeResolver.resolve(DeferredResult.class,
          typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
          typeResolver.resolve(WildcardType.class)))
      .useDefaultResponseMessages(false)
      .protocols(new HashSet<>(Arrays.asList("http", "https")))
      .select()
      .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
      .paths(Predicates.not(PathSelectors.regex("/error")))
      .build();
  }

  private static ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("Concrete Java Code Challenge")
      .description("Project to evaluate Java developers code skills")
      .license("MIT License")
      .licenseUrl("https://github.com/vitorsalgado/cs-desafio-java/blob/master/LICENSE")
      .termsOfServiceUrl("https://github.com/vitorsalgado/cs-desafio-java")
      .contact(new Contact("Vitor Hugo Salgado", "https://github.com/vitorsalgado", "vsalgadopb@gmail.com"))
      .version("1.0")
      .build();
  }
}
