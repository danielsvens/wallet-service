package coding.challenge.transactionservice.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class TransactionRegisteredResponse {

  @JsonProperty("currentBalance")
  private long balance;

  @JsonProperty("message")
  private String message;
}
