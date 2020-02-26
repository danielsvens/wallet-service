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

import java.util.List;
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
    var playerModel = playerRepository.findById(id);
    if (playerModel.isPresent()) {
      return PlayerResponse.builder()
              .id(playerModel.get().getId())
              .name(playerModel.get().getUserName())
              .transactionHistory(getTransactions(playerModel.get().getAccount().getId()))
              .currentBalance(playerModel.get().getAccount().getBalance())
              .build();
    }

    throw new PlayerNotFoundException("Player not found");
  }

  private Set<TransactionModel> getTransactions(long id) {
    return transactionRepository.findAllByAccountId(id);
  }
}
