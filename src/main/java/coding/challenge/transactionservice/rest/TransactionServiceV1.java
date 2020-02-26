package coding.challenge.transactionservice.rest;

import coding.challenge.transactionservice.pojo.Player;
import coding.challenge.transactionservice.pojo.Transaction;
import coding.challenge.transactionservice.response.PlayerCreatedResponse;
import coding.challenge.transactionservice.response.PlayerResponse;
import coding.challenge.transactionservice.response.TransactionRegisteredResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/v1/service")
public interface TransactionServiceV1 {

  @PostMapping(value = "/createPlayer", produces = {MediaType.APPLICATION_JSON_VALUE},
          consumes = {MediaType.APPLICATION_JSON_VALUE})
  ResponseEntity<PlayerCreatedResponse> create(@RequestBody @Valid Player player);

  @PutMapping(value = "/credit", produces = {MediaType.APPLICATION_JSON_VALUE},
          consumes = {MediaType.APPLICATION_JSON_VALUE})
  ResponseEntity<TransactionRegisteredResponse> credit(@RequestBody @Valid Transaction transaction);

  @PutMapping(value = "/debit", produces = {MediaType.APPLICATION_JSON_VALUE},
          consumes = {MediaType.APPLICATION_JSON_VALUE})
  ResponseEntity<TransactionRegisteredResponse> debit(@RequestBody @Valid Transaction transaction);

  @GetMapping(value = "/player/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
          consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<PlayerResponse> getPlayer(@PathVariable("id") Long id);
}
