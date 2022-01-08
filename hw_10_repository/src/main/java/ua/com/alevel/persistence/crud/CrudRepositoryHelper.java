package ua.com.alevel.persistence.crud;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.repository.AbstractRepository;

import java.util.Optional;

public interface CrudRepositoryHelper <ENT extends BaseEntity, REP extends AbstractRepository<ENT>>{

    void create(REP repository, ENT entity);
    void update(REP repository, ENT entity);

    void delete(REP repository, Long id);

    Optional<ENT> findById(REP repository, Long id);

    DataTableResponse<ENT> findAll(REP repository, DataTableRequest dataTableRequest);
}
