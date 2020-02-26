package coding.challenge.transactionservice.advice;

import coding.challenge.transactionservice.exception.PlayerNotFoundException;
import coding.challenge.transactionservice.pojo.ErrorRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class PlayerNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(PlayerNotFoundException.class)
  public ResponseEntity<ErrorRepresentation> handle(PlayerNotFoundException e) {
    ErrorRepresentation error = ErrorRepresentation.builder()
            .message(e.getMessage())
            .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }
}
