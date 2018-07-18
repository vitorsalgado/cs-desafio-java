package br.com.concrete.codechallenge.core.user.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import javax.validation.Valid;

import br.com.concrete.codechallenge.application.error.ErrorDto;
import br.com.concrete.codechallenge.core.usecases.registration.RegisterArgs;
import br.com.concrete.codechallenge.core.usecases.registration.RegisterInteractor;
import br.com.concrete.codechallenge.core.user.User;
import br.com.concrete.codechallenge.core.user.UserRepository;
import io.swagger.annotations.Api;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/users")
@Api(tags = "users")
class UserController {
  private final RegisterInteractor registerInteractor;
  private final UserRepository userRepository;

  UserController(final RegisterInteractor registerInteractor, final UserRepository userRepository) {
    this.registerInteractor = registerInteractor;
    this.userRepository = userRepository;
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  ResponseEntity<UserDto> create(@RequestBody @Valid RegistrationRequest req) {
    User user = registerInteractor.register(
      new RegisterArgs(req.getName(), req.getEmail(), req.getPassword(), req.phonesAsMap()));

    return ResponseEntity
      .created(newUserLocation(user))
      .body(UserDto.mapFrom(user));
  }

  @GetMapping("/{id}")
  @ResponseStatus(code = HttpStatus.OK)
  ResponseEntity<?> get(@AuthenticationPrincipal String token, @PathVariable String id) {
    return userRepository
      .findById(id)
      .map(user -> ResponseEntity.ok(UserDto.mapFrom(user)))
      .orElseGet(() -> new ResponseEntity(new ErrorDto("Usuário não encontrado"), NOT_FOUND));
  }

  private static URI newUserLocation(User user) {
    return URI.create(format("/users/%s", user.getId()));
  }
}
