package coding.challenge.transactionservice.pojo;

import coding.challenge.transactionservice.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

  @NotNull
  @JsonProperty("transactionId")
  private UUID transactionId;

  @NotNull
  @JsonProperty("amount")
  private long amount;

  @NotNull
  @JsonProperty("playerId")
  private long playerId;

  private TransactionType type;
}
