package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.Operation;

import java.util.Set;

public interface AccountDao extends BaseDao<Account>{

    Set<Operation> getOperations(Long accountId);

    void addOperation(Long accountId, Long incomeId);

    void removeOperation(Long accountId, Long incomeId);

    Account getLastRecord();
}
