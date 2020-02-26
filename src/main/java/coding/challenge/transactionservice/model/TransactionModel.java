package coding.challenge.transactionservice.model;

import coding.challenge.transactionservice.Enum.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class TransactionModel implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @Column(name = "transaction_id", unique = true)
  private UUID transactionId;

  @Column(name = "amount")
  private Long amount;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private TransactionType type;

  @Column(name = "created")
  private LocalDateTime date;

  @Column(name = "account_id")
  private Long accountId;
}
