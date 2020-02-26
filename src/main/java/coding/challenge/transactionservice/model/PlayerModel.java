package coding.challenge.transactionservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "player")
public class PlayerModel implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "user", unique = true)
  private String userName;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "account_id", referencedColumnName = "id")
  private AccountModel account;
}
