package br.com.concrete.codechallenge.core.user.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.com.concrete.codechallenge.core.user.RegisterArgs;
import br.com.concrete.codechallenge.core.user.RegisterService;
import br.com.concrete.codechallenge.core.user.User;
import br.com.concrete.codechallenge.core.user.UserRepository;
import io.swagger.annotations.Api;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/users")
@Api(tags = "users")
class UserController {
  private final RegisterService registerService;
  private final UserRepository userRepository;

  UserController(final RegisterService registerService, final UserRepository userRepository) {
    this.registerService = registerService;
    this.userRepository = userRepository;
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  ResponseEntity<UserDto> create(@RequestBody @Valid RegistrationRequest req) {
    User user = registerService.register(
      new RegisterArgs(req.getName(), req.getEmail(), req.getPassword(), req.phonesAsMap()));

    return ResponseEntity
      .created(newUserLocation(user))
      .body(UserDto.mapFrom(user));
  }

  @GetMapping("/{id}")
  @ResponseStatus(code = HttpStatus.OK)
  ResponseEntity<?> get(@PathVariable String id) {
    return userRepository
      .findById(id)
      .map(user -> ResponseEntity.ok(UserDto.mapFrom(user)))
      .orElseGet(() -> new ResponseEntity(new ErrorDto("User not found"), NOT_FOUND));
  }

  private static URI newUserLocation(User user) {
    return URI.create(format("/users/%s", user.getId()));
  }
}
