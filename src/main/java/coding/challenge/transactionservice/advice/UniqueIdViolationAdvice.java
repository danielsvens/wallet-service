package coding.challenge.transactionservice.advice;

import coding.challenge.transactionservice.exception.UniqueIdViolationException;
import coding.challenge.transactionservice.pojo.ErrorRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class UniqueIdViolationAdvice {

  @ResponseBody
  @ExceptionHandler(UniqueIdViolationException.class)
  public ResponseEntity<ErrorRepresentation> handle(UniqueIdViolationException e) {
    ErrorRepresentation error = ErrorRepresentation.builder()
            .message(e.getMessage())
            .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }
}
