package ua.com.alevel.view.dto.request;

import ua.com.alevel.persistence.entity.Account;

public class OperationRequestDto extends RequestDto{

    private Double value;

    private Account account;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
