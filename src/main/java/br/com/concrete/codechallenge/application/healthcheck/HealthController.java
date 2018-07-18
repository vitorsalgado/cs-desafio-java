package br.com.concrete.codechallenge.application.healthcheck;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/health")
@Api(tags = "health")
class HealthController {
  @GetMapping
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Checks if Server is online")
  public ResponseEntity check() {
    return ResponseEntity.noContent().build();
  }
}
