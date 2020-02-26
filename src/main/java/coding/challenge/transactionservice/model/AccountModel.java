package coding.challenge.transactionservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class AccountModel implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "balance")
  private Long balance;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "account_id")
  private Set<TransactionModel> transactions = new HashSet<>();

  @OneToOne(mappedBy = "account")
  private PlayerModel player;
}
