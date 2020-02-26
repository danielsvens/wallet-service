package coding.challenge.transactionservice.exception;

public class UniqueIdViolationException extends RuntimeException {

  public UniqueIdViolationException(String msg) {
    super(msg);
  }
}
