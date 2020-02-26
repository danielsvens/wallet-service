package coding.challenge.transactionservice.response;

import coding.challenge.transactionservice.model.TransactionModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Set;

@Builder
public class PlayerResponse {

  @JsonProperty("name")
  private String name;

  @JsonProperty("playerId")
  private long id;

  @JsonProperty("currentBalance")
  private long currentBalance;

  @JsonProperty("transactionHistory")
  private Set<TransactionModel> transactionHistory;
}
