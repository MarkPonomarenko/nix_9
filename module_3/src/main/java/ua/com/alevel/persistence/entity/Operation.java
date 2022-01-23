package ua.com.alevel.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "operations")
public class Operation extends BaseEntity{

    private Double value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="account_id", nullable = false)
    private Account accountIn;

    public Account getAccountIn() {
        return accountIn;
    }

    public void setAccountIn(Account accountIn) {
        this.accountIn = accountIn;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
