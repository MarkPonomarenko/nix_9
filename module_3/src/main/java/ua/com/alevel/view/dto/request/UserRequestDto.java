package ua.com.alevel.view.dto.request;

import ua.com.alevel.persistence.entity.Account;

import java.util.Set;

public class UserRequestDto extends RequestDto{

    private String phone;

    private String name;

    private String password;

    private Set<Account> accounts;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
