package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.User;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;

public class UserResponseDto extends ResponseDto{

    private String phone;

    private String name;

    private String password;

    private Set<Account> accounts;

    private Instant time;

    public UserResponseDto() {

    }

    public UserResponseDto(User user) {
        super.setId(user.getId());
        this.phone = user.getPhone();
        this.time = user.getCreated().toInstant();
        this.name = user.getName();
        this.password = user.getPassword();
        this.accounts = user.getAccounts();
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
