package coding.challenge.transactionservice.pojo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ErrorRepresentation {

  @JsonProperty("message")
  private String message;
}
