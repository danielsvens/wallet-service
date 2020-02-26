package coding.challenge.transactionservice.service;

import coding.challenge.transactionservice.Enum.TransactionType;
import coding.challenge.transactionservice.exception.InsufficientFundsException;
import coding.challenge.transactionservice.exception.PlayerNotFoundException;
import coding.challenge.transactionservice.exception.UniqueIdViolationException;
import coding.challenge.transactionservice.model.AccountModel;
import coding.challenge.transactionservice.model.PlayerModel;
import coding.challenge.transactionservice.model.TransactionModel;
import coding.challenge.transactionservice.pojo.Transaction;
import coding.challenge.transactionservice.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AccountService {

  private PlayerRepository playerRepository;

  @Autowired
  AccountService(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  public long credit(Transaction transaction) {
    transaction.setType(TransactionType.CREDIT);
    var player = playerRepository.findById(transaction.getPlayerId());

    player.ifPresent(playerModel -> makeTransaction(playerModel, transaction));

    return player.get().getAccount().getBalance();
  }

  public long debit(Transaction transaction) {
    transaction.setType(TransactionType.DEBIT);
    var player = playerRepository.findById(transaction.getPlayerId());
    if (player.isPresent()) {
      if (!isValidDebit(player.get().getAccount(), transaction)) {
        throw new InsufficientFundsException("Not enough funds to withdraw from");
      }

      makeTransaction(player.get(), transaction);
      return player.get().getAccount().getBalance();
    }

    throw new PlayerNotFoundException("Player not found");
  }

  private void makeTransaction(PlayerModel player, Transaction transaction) {
    try {
      registerTransaction(player.getAccount(), transaction);
      modifyBalance(player.getAccount(), transaction);
      playerRepository.save(player);
    } catch (DataIntegrityViolationException e) {
      throw new UniqueIdViolationException("TransactionId must be unique.");
    }
  }

  private boolean isValidDebit(AccountModel account, Transaction transaction) {
    return account.getBalance() - transaction.getAmount() >= 0;
  }

  private void registerTransaction(AccountModel account, Transaction transaction) {
    account.getTransactions().add(TransactionModel.builder()
            .amount(getTransactionAmount(transaction))
            .date(LocalDateTime.now())
            .transactionId(transaction.getTransactionId())
            .type(transaction.getType())
            .build());
  }

  private void modifyBalance(AccountModel account, Transaction transaction) {
    switch (transaction.getType()) {
      case DEBIT:
        account.setBalance(account.getBalance() - transaction.getAmount());
        break;
      case CREDIT:
        account.setBalance(account.getBalance() + transaction.getAmount());
        break;
      default:
        throw new IllegalArgumentException("Invalid type");
    }
  }

  private Long getTransactionAmount(Transaction transaction) {
    return transaction.getType() == TransactionType.DEBIT ? -transaction.getAmount() : transaction.getAmount();
  }
}
