package coding.challenge.transactionservice.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class PlayerCreatedResponse {

  @JsonProperty("currentBalance")
  private long balance;

  @JsonProperty("playerId")
  private long id;
}
