package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.provider.Provider;
import ua.com.alevel.persistence.repository.server.ProviderRepository;
import ua.com.alevel.service.ProviderService;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;
    private final CrudRepositoryHelper<Provider, ProviderRepository> crudRepositoryHelper;

    public ProviderServiceImpl(ProviderRepository providerRepository, CrudRepositoryHelper<Provider, ProviderRepository> crudRepositoryHelper) {
        this.providerRepository = providerRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void create(Provider entity) {
        crudRepositoryHelper.create(providerRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void update(Provider entity) {
        crudRepositoryHelper.update(providerRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void delete(Long id) {
        crudRepositoryHelper.delete(providerRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Provider> findById(Long id) {
        return crudRepositoryHelper.findById(providerRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<Provider> findAll(DataTableRequest request) {
        return crudRepositoryHelper.findAll(providerRepository, request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Provider> findAll() {
        return providerRepository.findAll();
    }
}
