package br.com.concrete.codechallenge.application.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import br.com.concrete.codechallenge.core.DomainException;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
class GlobalErrorController implements ErrorController {
  @RequestMapping("/error")
  ResponseEntity<ErrorDto> handleError(HttpServletRequest request) throws Throwable {
    if (request.getAttribute("javax.servlet.error.exception") != null) {
      final Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");

      if (throwable instanceof DomainException) {
        final DomainException exception = (DomainException) throwable;
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()), exception.getHttpStatus());
      }

      return new ResponseEntity<>(new ErrorDto(throwable.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    throw (Throwable) request.getAttribute("javax.servlet.error.exception");
  }

  @Override
  public String getErrorPath() {
    return null;
  }
}
