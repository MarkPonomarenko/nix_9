package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.server.Server;

import java.util.List;

public interface ServerService extends BaseCrudService<Server> {

    List<Server> findAll();
}
