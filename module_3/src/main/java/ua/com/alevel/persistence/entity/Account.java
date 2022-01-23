package ua.com.alevel.persistence.entity;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity{

    private Double balance;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="owner_id", nullable = false)
    private User owner;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "accountIn")
    private Set<Operation> operations;

    public void addOperation(Operation operation) {
        operations.add(operation);
        operation.setAccountIn(this);
    }

    public void removeOperation(Operation operation) {
        System.out.println(operation.getId());
        operations.remove(operation);
        operation.setAccountIn(null);
    }

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
