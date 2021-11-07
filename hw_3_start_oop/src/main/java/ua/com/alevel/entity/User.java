package ua.com.alevel.entity;

public class User {
    private int age;
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Пользователь{ID = \'"
                + id + '\'' +
                ", Имя = \'" + name + '\'' +
                ", Возраст = " + age + '}';
    }
}
