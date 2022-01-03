package ua.com.alevel.service;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;

import java.util.List;

public interface BaseService<ENT extends BaseEntity> {

    void create(ENT ent);

    void update(ENT ent);

    void delete(Long id);

    ENT findById(Long id);

    List<ENT> findAll();

    DataTableResponse<ENT> findAll(DataTableRequest request);
}
