package ua.com.alevel.service;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;

public interface BaseService <ENT extends BaseEntity>{

    void create(ENT entity);

    void update(ENT entity);

    void delete(Long id);

    ENT findById(Long id);

    DataTableResponse<ENT> findAll(DataTableRequest request);

}
