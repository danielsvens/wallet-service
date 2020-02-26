package coding.challenge.transactionservice.service;

import coding.challenge.transactionservice.exception.PlayerNotFoundException;
import coding.challenge.transactionservice.model.AccountModel;
import coding.challenge.transactionservice.model.PlayerModel;
import coding.challenge.transactionservice.model.TransactionModel;
import coding.challenge.transactionservice.pojo.Player;
import coding.challenge.transactionservice.repository.PlayerRepository;
import coding.challenge.transactionservice.repository.TransactionRepository;
import coding.challenge.transactionservice.response.PlayerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PlayerService {

  private PlayerRepository playerRepository;
  private TransactionRepository transactionRepository;

  @Autowired
  PlayerService(PlayerRepository playerRepository, TransactionRepository transactionRepository) {
    this.playerRepository = playerRepository;
    this.transactionRepository = transactionRepository;
  }

  public long createNewPlayer(Player player) {
    return playerRepository.save(PlayerModel.builder()
            .userName(player.getName())
            .account(AccountModel.builder().balance(0L).build())
            .build()).getId();
  }

  public PlayerResponse getPlayer(long id) {
    var playerModel = playerRepository.findById(id)
            .orElseThrow(() -> new PlayerNotFoundException("Player not found"));

    return PlayerResponse.builder()
            .id(playerModel.getId())
            .name(playerModel.getUserName())
            .transactionHistory(getTransactions(playerModel.getAccount().getId()))
            .currentBalance(playerModel.getAccount().getBalance())
            .build();
  }

  private Set<TransactionModel> getTransactions(long id) {
    return transactionRepository.findAllByAccountId(id);
  }
}
