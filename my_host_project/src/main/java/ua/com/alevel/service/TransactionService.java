package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.transactions.Transaction;

import java.util.List;

public interface TransactionService extends BaseCrudService<Transaction>{

    List<Transaction> findAll();
}
