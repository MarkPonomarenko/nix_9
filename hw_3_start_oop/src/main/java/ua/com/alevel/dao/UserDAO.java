package ua.com.alevel.dao;

import ua.com.alevel.entity.User;
import ua.com.alevel.db.UserDataBase;

public class UserDAO {

    public void create(User user) {
        UserDataBase.getInstance().create(user);
    }

    public void update(User user) {
        UserDataBase.getInstance().update(user);
    }

    public void delete(String id) {
        UserDataBase.getInstance().delete(id);
    }

    public User findById(String id) {
        return UserDataBase.getInstance().findById(id);
    }

    public User[] findAll() {
        return UserDataBase.getInstance().findAll();
    }
}
