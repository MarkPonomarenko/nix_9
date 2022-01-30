package ua.com.alevel.persistence.entity.user;

import ua.com.alevel.persistence.type.Roles;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    public Admin() {
        super();
        setRole(Roles.ROLE_ADMIN);
    }
}