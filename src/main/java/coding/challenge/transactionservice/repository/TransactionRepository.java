package coding.challenge.transactionservice.repository;

import coding.challenge.transactionservice.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {

  Set<TransactionModel> findAllByAccountId(long id);
}
