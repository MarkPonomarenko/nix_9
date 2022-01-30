package ua.com.alevel.persistence.listener;

import ua.com.alevel.persistence.entity.server.Server;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

public class ServerVisibleGenerationListener {

    @PostLoad
    @PostPersist
    @PostUpdate
    public void generateServerVisible(Server server) {
        if (server.getPersonal() != null) {
            server.setVisible(false);
        } else {
            server.setVisible(true);
        }
    }
}
