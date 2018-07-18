package br.com.concrete.codechallenge.core.account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import br.com.concrete.codechallenge.core.user.User;
import br.com.concrete.codechallenge.core.user.controllers.UserDto;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/account")
@Api(tags = "account")
class AccountController {
  private final AccountService accountService;

  AccountController(final AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping("/login")
  ResponseEntity<UserDto> login(@RequestBody @Valid LoginRequest req) {
    User user = accountService.login(req);

    return ResponseEntity
      .ok(UserDto.mapFrom(user));
  }
}
