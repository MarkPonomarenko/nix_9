package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.provider.Provider;

import java.util.List;

public interface ProviderService extends BaseCrudService<Provider> {

    List<Provider> findAll();
}
