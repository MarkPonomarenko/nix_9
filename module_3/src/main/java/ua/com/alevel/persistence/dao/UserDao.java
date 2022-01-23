package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.User;

import java.util.List;
import java.util.Set;

public interface UserDao extends BaseDao<User>{

    Set<Account> getAccounts(Long userId);

    void addAccount(Long userId, Long accountId);

    void removeAccount(Long userId, Long accountId);

    List<User> findAll();
}
