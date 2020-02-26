package coding.challenge.transactionservice.service;

import coding.challenge.transactionservice.enums.TransactionType;
import coding.challenge.transactionservice.exception.InsufficientFundsException;
import coding.challenge.transactionservice.exception.UniqueIdViolationException;
import coding.challenge.transactionservice.model.AccountModel;
import coding.challenge.transactionservice.model.PlayerModel;
import coding.challenge.transactionservice.pojo.Transaction;
import coding.challenge.transactionservice.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  @Mock
  private PlayerRepository playerRepository;

  @InjectMocks
  private AccountService accountService;

  @BeforeEach
  void setUp() {
    when(playerRepository.findById(any())).thenReturn(basePlayerResponse());
  }

  @Test
  void testCorrectCreditBalanceOnRegisterTransaction() {
    final long BALANCE = 50L;
    long result = 0L;

    for (int i = 0; i < 5; i++) {
      result = accountService.registerTransaction(baseTransaction(TransactionType.CREDIT));
    }

    assertEquals(BALANCE, result);
    verify(playerRepository, times(5)).save(any());
  }

  @Test
  void testCorrectDebitBalanceOnRegisterTransaction() {
    final long BALANCE = 40L;
    for (int i = 0; i < 5; i++) {
      accountService.registerTransaction(baseTransaction(TransactionType.CREDIT));
    }

    long result = accountService.registerTransaction(baseTransaction(TransactionType.DEBIT));

    assertEquals(BALANCE, result);
    verify(playerRepository, times(6)).save(any());
  }

  @Test
  void testDebitOnInvalidAmount() {
    var transaction = baseTransaction(TransactionType.DEBIT);

    Throwable exception = assertThrows(InsufficientFundsException.class,
            () -> accountService.registerTransaction(transaction));

    assertEquals("Not enough funds to withdraw from", exception.getMessage());
    verify(playerRepository, times(0)).save(any());
  }

  @Test
  void testInvalidTransactionId() {
    when(playerRepository.save(any())).thenThrow(new DataIntegrityViolationException("Exception"));
    var transaction = baseTransaction(TransactionType.CREDIT);

    Throwable exception = assertThrows(UniqueIdViolationException.class,
            () -> accountService.registerTransaction(transaction));

    assertEquals("TransactionId must be unique.", exception.getMessage());
  }

  private Transaction baseTransaction(TransactionType type) {
    return Transaction.builder()
            .amount(10L)
            .playerId(1L)
            .transactionId(UUID.randomUUID())
            .type(type)
            .build();
  }

  private Optional<PlayerModel> basePlayerResponse() {
    return Optional.of(PlayerModel.builder()
            .id(1L)
            .userName("Daniel")
            .account(AccountModel.builder().balance(0L).id(1L).transactions(new HashSet<>()).build())
            .build());
  }
}
