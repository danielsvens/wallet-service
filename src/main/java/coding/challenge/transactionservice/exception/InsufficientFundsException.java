package coding.challenge.transactionservice.exception;

public class InsufficientFundsException extends RuntimeException {

  public InsufficientFundsException(String msg) {
    super(msg);
  }
}
