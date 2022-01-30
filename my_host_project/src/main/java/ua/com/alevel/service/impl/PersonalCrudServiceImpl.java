package ua.com.alevel.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.server.Server;
import ua.com.alevel.persistence.entity.user.Personal;
import ua.com.alevel.persistence.repository.server.ServerRepository;
import ua.com.alevel.persistence.repository.user.PersonalRepository;
import ua.com.alevel.service.PersonalCrudService;
import ua.com.alevel.service.ServerService;

import java.util.List;
import java.util.Optional;

@Service
public class PersonalCrudServiceImpl implements PersonalCrudService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PersonalRepository personalRepository;
    private final ServerRepository serverRepository;
    private final CrudRepositoryHelper<Server, ServerRepository> crudRepositoryHelperServer;
    private final CrudRepositoryHelper<Personal, PersonalRepository> crudRepositoryHelper;

    public PersonalCrudServiceImpl(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            PersonalRepository personalRepository, ServerService serverService, ServerRepository serverRepository, CrudRepositoryHelper<Personal, PersonalRepository> crudRepositoryHelper, CrudRepositoryHelper<Server, ServerRepository> crudRepositoryHelperServer) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.personalRepository = personalRepository;
        this.serverRepository = serverRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
        this.crudRepositoryHelperServer = crudRepositoryHelperServer;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void create(Personal personal) {
        if (personalRepository.existsByEmail(personal.getEmail())) {
            throw new EntityExistException("this personal exists");
        }
        personal.setPassword(bCryptPasswordEncoder.encode(personal.getPassword()));
        crudRepositoryHelper.create(personalRepository, personal);
    }

    @Override
    public void update(Personal personal) {
        if (!personalRepository.existsByEmail(personal.getEmail())) {
            throw new EntityExistException("this personal doesn't exist");
        }
        Personal updater = personalRepository.findByEmail(personal.getEmail());
        updater.setLastName(personal.getLastName());
        updater.setFirstName(personal.getFirstName());
        updater.setBalance(personal.getBalance());
        updater.setRented(personal.getRented());
        crudRepositoryHelper.update(personalRepository, personal);
    }

    @Override
    public void delete(Long id) {
        for (Server server : findById(id).get().getRented()) {
            server.setPersonal(null);
            server.setVisible(true);
            crudRepositoryHelperServer.update(serverRepository, server);
        }
        crudRepositoryHelper.delete(personalRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Personal> findById(Long id) {
        return crudRepositoryHelper.findById(personalRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<Personal> findAll(DataTableRequest request) {
        return crudRepositoryHelper.findAll(personalRepository, request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personal> findAll() {
        return personalRepository.findAll();
    }

    @Override
    public void removeRentedById(Personal personal, Long serverId) {

        Server server = serverRepository.findById(serverId).get();
        System.out.println(server.getServerName());
        personal.removeRented(server);
    }
}
