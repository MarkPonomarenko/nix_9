package ua.com.alevel.view.dto.request;

import ua.com.alevel.persistence.entity.Operation;
import ua.com.alevel.persistence.entity.User;

import java.util.Set;

public class AccountRequestDto extends RequestDto{

    private Double balance;

    private User owner;

    private Set<Operation> operations;


    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }
}
