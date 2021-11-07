package ua.com.alevel.db;

import ua.com.alevel.entity.User;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class UserDataBase {
    private User[] users;
    private static UserDataBase instance;

    private UserDataBase() {
        users = new User[0];
    }

    public static UserDataBase getInstance() {
        if (instance == null) {
            instance = new UserDataBase();
        }
        return instance;
    }

    private void add(User user) {
        users = Arrays.copyOf(users, users.length + 1);
        users[users.length - 1] = user;
    }

    public void create(User user) {
        user.setId(generateId());
        this.add(user);
    }

    public void update(User user) {
        User current = findById(user.getId());
        current.setAge(user.getAge());
        current.setName(user.getName());
    }

    public User[] moveNull(User[] prevArr, int index) {
        if (prevArr == null) return prevArr;
        User[] newArray = new User[prevArr.length - 1];
        for (int i = 0, k = 0; i < prevArr.length; ++i) {
            if (i == index) {
                continue;
            }
            newArray[k++] = prevArr[i];
        }
        return newArray;
    }

    public void remove(int index) {
        users[index] = null;
        users = moveNull(users, index);
    }

    public void delete(String id) {
        int index;
        if ((index = checkMatchInd(users, id)) != -1) {
            this.remove(index);
        }
    }

    public User findById(String idEx) {
        if (users == null) {
            return null;
        }
        return Arrays.stream(users)
                .filter(u -> u.getId().equals(idEx))
                .findFirst()
                .orElse(null);
    }

    private int checkMatchInd(User[] userArr, String idEx) {
        int i = 0;
        for (User userIt : userArr) {
            if (Objects.equals(userIt.getId(), idEx)) return i;
            ++i;
        }
        return -1;
    }

    public User[] findAll() {
        return users;
    }

    private boolean checkMatch(User[] userArr, String idEx) {
        for (User userIt : userArr) {
            if (Objects.equals(userIt.getId(), idEx)) return true;
        }
        return false;
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        if (checkMatch(users, id)) return generateId();
        return id;
    }
}
