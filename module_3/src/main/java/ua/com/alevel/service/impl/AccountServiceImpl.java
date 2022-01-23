package ua.com.alevel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.dao.AccountDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.Operation;
import ua.com.alevel.service.AccountService;

import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger("info");

    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
        LOGGER.info("account created");
    }

    @Override
    public Set<Operation> getOperations(Long accountId) {
        LOGGER.info("all account's operations found");
        return accountDao.getOperations(accountId);
    }

    @Override
    public void addOperation(Long accountId, Long incomeId) {
        accountDao.addOperation(accountId, incomeId);
        LOGGER.info("added operation to account");
    }

    @Override
    public void removeOperation(Long accountId, Long incomeId) {
        accountDao.removeOperation(accountId, incomeId);
        LOGGER.info("removed operation from account");
    }

    @Override
    public void create(Account entity) {
        accountDao.create(entity);
        LOGGER.info("account created");
    }

    @Override
    public void update(Account entity) {
        if (!accountDao.existById(entity.getId())) {
            LOGGER.info("account " + entity.getId() + " not found for update");
            throw new EntityNotFoundException("account not found");
        }
        accountDao.update(entity);
        LOGGER.info("account " + entity.getId() + " updated");
    }

    @Override
    public void delete(Long id) {
        if (!accountDao.existById(id)) {
            LOGGER.info("account " + id + " not found for delete");
            throw new EntityNotFoundException("account not found");
        }
        accountDao.delete(id);
        LOGGER.info("account " + id + " deleted");
    }

    @Override
    public Account findById(Long id) {
        if (!accountDao.existById(id)) {
            LOGGER.info("account " + id + " not found");
            throw new EntityNotFoundException("account not found");
        }
        LOGGER.info("account " + id + " found");
        return accountDao.findById(id);
    }

    @Override
    public DataTableResponse<Account> findAll(DataTableRequest request) {
        LOGGER.info("all accounts found (DataTableResponse)");
        return accountDao.findAll(request);
    }

    @Override
    public Account getLastRecord() {
        LOGGER.info("get last account recorded");
        return accountDao.getLastRecord();
    }
}
