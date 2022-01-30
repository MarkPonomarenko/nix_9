package ua.com.alevel.persistence.repository.transactions;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.transactions.Transaction;
import ua.com.alevel.persistence.repository.BaseRepository;

import java.util.List;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction> {

    List<Transaction> findByEmail(String email);
}
