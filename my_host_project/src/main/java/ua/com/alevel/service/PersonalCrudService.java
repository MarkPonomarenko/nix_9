package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.user.Personal;

import java.util.List;

public interface PersonalCrudService extends BaseCrudService<Personal> {
    List<Personal> findAll();
}
