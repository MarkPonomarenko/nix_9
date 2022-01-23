package ua.com.alevel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.dao.UserDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.service.UserService;


import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger("info");

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void create(User entity) {
        userDao.create(entity);
        LOGGER.info("user created");
    }

    @Override
    public void update(User entity) {
        if (!userDao.existById(entity.getId())) {
            LOGGER.info("user " + entity.getId() + " not found for update");
            throw new EntityNotFoundException("user not found");
        }
        userDao.update(entity);
        LOGGER.info("user " + entity.getId() + " updated");
    }

    @Override
    public void delete(Long id) {
        if (!userDao.existById(id)) {
            LOGGER.info("user " + id + " not found for delete");
            throw new EntityNotFoundException("user not found");
        }
        userDao.delete(id);
        LOGGER.info("user " + id + " deleted");
    }

    @Override
    public User findById(Long id) {
        if (!userDao.existById(id)) {
            LOGGER.info("user " + id + " not found");
            throw new EntityNotFoundException("user not found");
        }
        LOGGER.info("user " + id + " found");
        return userDao.findById(id);
    }

    @Override
    public DataTableResponse<User> findAll(DataTableRequest request) {
        LOGGER.info("all users found (DataTableResponse)");
        return userDao.findAll(request);

    }

    @Override
    public List<User> findAll() {
        LOGGER.info("all users found (List)");
        return userDao.findAll();
    }

    @Override
    public Set<Account> getAccounts(Long userId) {
        LOGGER.info("all user's accounts found");
        return userDao.getAccounts(userId);
    }

    @Override
    public void addAccount(Long userId, Long accountId) {
        userDao.addAccount(userId, accountId);
        LOGGER.info("added account to user");
    }

    @Override
    public void removeAccount(Long userId, Long accountId) {
        userDao.removeAccount(userId, accountId);
        LOGGER.info("removed account from user");
    }
}
