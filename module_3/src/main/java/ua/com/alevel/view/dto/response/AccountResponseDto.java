package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.Operation;
import ua.com.alevel.persistence.entity.User;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class AccountResponseDto extends ResponseDto{

    private Double balance;

    private User owner;

    private Set<Operation> operations;

    private Instant time;

    public AccountResponseDto() {

    }

    public AccountResponseDto(Account account) {
        super.setId(account.getId());
        this.time = account.getCreated().toInstant();
        this.balance = account.getBalance();
        this.operations = account.getOperations();
        this.owner = account.getOwner();
    }

    public String getTimeString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.from(ZoneOffset.UTC));
        String formatted = formatter.format(this.time);
        formatted = formatted.replaceAll("[TZ]", " ");
        return formatted;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
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
