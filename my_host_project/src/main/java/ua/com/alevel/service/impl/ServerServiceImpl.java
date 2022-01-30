package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.provider.Provider;
import ua.com.alevel.persistence.entity.server.Server;
import ua.com.alevel.persistence.entity.user.Personal;
import ua.com.alevel.persistence.repository.server.ServerRepository;
import ua.com.alevel.persistence.repository.user.PersonalRepository;
import ua.com.alevel.service.ProviderService;
import ua.com.alevel.service.ServerService;

import java.util.List;
import java.util.Optional;

@Service
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;
    private final ProviderService providerService;
    private final PersonalRepository personalRepository;
    private final CrudRepositoryHelper<Personal, PersonalRepository> crudRepositoryHelperPersonal;
    private final CrudRepositoryHelper<Server, ServerRepository> crudRepositoryHelper;

    public ServerServiceImpl(ServerRepository serverRepository, ProviderService providerService, PersonalRepository personalRepository, CrudRepositoryHelper<Personal, PersonalRepository> crudRepositoryHelperPersonal, CrudRepositoryHelper<Server, ServerRepository> crudRepositoryHelper) {
        this.serverRepository = serverRepository;
        this.providerService = providerService;
        this.personalRepository = personalRepository;
        this.crudRepositoryHelperPersonal = crudRepositoryHelperPersonal;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }


    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void create(Server entity) {
        Provider provider = entity.getProvider();
        provider.addServer(entity);
        providerService.update(provider);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void update(Server entity) {
        crudRepositoryHelper.update(serverRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void delete(Long id) {
        if (findById(id).get().getPersonal() != null) {
            System.out.println(findById(id).get() + "triggered user update");
            Personal personal = findById(id).get().getPersonal();
            personal.removeRented(findById(id).get());
            crudRepositoryHelperPersonal.update(personalRepository, personal);
        }
        System.out.println(findById(id).get() + "deleted");
        crudRepositoryHelper.delete(serverRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Server> findById(Long id) {
        return crudRepositoryHelper.findById(serverRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<Server> findAll(DataTableRequest request) {
        return crudRepositoryHelper.findAll(serverRepository, request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Server> findAll() {
        return serverRepository.findAll();
    }
}
