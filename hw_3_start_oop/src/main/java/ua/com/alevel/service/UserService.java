package ua.com.alevel.service;

import ua.com.alevel.dao.UserDAO;
import ua.com.alevel.entity.User;

public class UserService {

    private final UserDAO userDao = new UserDAO();

    public void create(User user) {
        userDao.create(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(String id) {
        userDao.delete(id);
    }

    public User findById(String id) {
        return userDao.findById(id);
    }

    public User[] findAll() {
        return userDao.findAll();
    }
}
