package coding.challenge.transactionservice.rest;

import coding.challenge.transactionservice.pojo.Player;
import coding.challenge.transactionservice.pojo.Transaction;
import coding.challenge.transactionservice.response.PlayerCreatedResponse;
import coding.challenge.transactionservice.response.PlayerResponse;
import coding.challenge.transactionservice.response.TransactionRegisteredResponse;
import coding.challenge.transactionservice.service.AccountService;
import coding.challenge.transactionservice.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TransactionServiceV1Controller implements TransactionServiceV1 {

  private AccountService accountService;
  private PlayerService playerService;

  @Autowired
  TransactionServiceV1Controller(AccountService accountService, PlayerService playerService) {
    this.accountService = accountService;
    this.playerService = playerService;
  }

  @Override
  public ResponseEntity<PlayerCreatedResponse> create(@Valid Player player) {
    long playerId = playerService.createNewPlayer(player);
    var created = PlayerCreatedResponse.builder().balance(0L).id(playerId).build();

    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<TransactionRegisteredResponse> credit(@Valid Transaction transaction) {
    long balance = accountService.credit(transaction);
    var transactionResponse = TransactionRegisteredResponse.builder()
            .balance(balance)
            .message("Amount: " + transaction.getAmount() + " was successfully added to funds.")
            .build();

    return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<TransactionRegisteredResponse> debit(@Valid Transaction transaction) {
    long balance = accountService.debit(transaction);
    var transactionResponse = TransactionRegisteredResponse.builder()
            .balance(balance)
            .message("Amount: " + -transaction.getAmount() + " was successfully withdrawn from funds")
            .build();

    return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<PlayerResponse> getPlayer(Long id) {
    var player = playerService.getPlayer(id);
    return new ResponseEntity<>(player, HttpStatus.OK);
  }
}
