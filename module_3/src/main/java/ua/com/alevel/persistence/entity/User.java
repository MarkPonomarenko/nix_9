package ua.com.alevel.persistence.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    private String password;

    private String name;

    private String phone;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Account> accounts;

    public User() {
        super();
        accounts = new HashSet<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
        account.setOwner(this);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        account.setBalance(null);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
