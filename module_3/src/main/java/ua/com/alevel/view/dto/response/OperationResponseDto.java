package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.Operation;

import java.time.Instant;
import java.util.Date;

public class OperationResponseDto extends ResponseDto{

    private Double value;

    private Account owner;

    private Date time;

    public OperationResponseDto() {

    }

    public OperationResponseDto(Operation operation) {
        super.setId(operation.getId());
        this.time = operation.getCreated();
        this.value = operation.getValue();
        this.owner = operation.getAccountIn();
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
