package coding.challenge.transactionservice.rest;

import coding.challenge.transactionservice.enums.TransactionType;
import coding.challenge.transactionservice.exception.UniqueIdViolationException;
import coding.challenge.transactionservice.pojo.Player;
import coding.challenge.transactionservice.pojo.Transaction;
import coding.challenge.transactionservice.response.PlayerCreatedResponse;
import coding.challenge.transactionservice.response.PlayerResponse;
import coding.challenge.transactionservice.response.TransactionRegisteredResponse;
import coding.challenge.transactionservice.service.AccountService;
import coding.challenge.transactionservice.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
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
    long playerId;

    try {
      playerId = playerService.createNewPlayer(player);
    } catch (DataIntegrityViolationException e) {
      log.error(e.getMessage());
      throw new UniqueIdViolationException("Player already exist.");
    }

    var created = PlayerCreatedResponse.builder().balance(0L).id(playerId).build();
    log.info("New played created with id: {}", playerId);

    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<TransactionRegisteredResponse> credit(@Valid Transaction transaction) {
    transaction.setType(TransactionType.CREDIT);
    long balance = accountService.registerTransaction(transaction);
    var transactionResponse = TransactionRegisteredResponse.builder()
            .balance(balance)
            .message("Amount: " + transaction.getAmount() + " was successfully added to funds.")
            .build();

    log.info("Added {} funds to account", balance);

    return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<TransactionRegisteredResponse> debit(@Valid Transaction transaction) {
    transaction.setType(TransactionType.DEBIT);
    long balance = accountService.registerTransaction(transaction);
    var transactionResponse = TransactionRegisteredResponse.builder()
            .balance(balance)
            .message("Amount: " + -transaction.getAmount() + " was successfully withdrawn from funds")
            .build();

    log.info("Withdrew {} funds from account", balance);

    return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<PlayerResponse> getPlayer(Long id) {
    var player = playerService.getPlayer(id);
    return new ResponseEntity<>(player, HttpStatus.OK);
  }
}
