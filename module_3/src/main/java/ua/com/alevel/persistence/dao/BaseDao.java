package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;


public interface BaseDao<ENT extends BaseEntity> {

    void create(ENT entity);

    void update(ENT entity);

    void delete(Long id);

    boolean existById(Long id);

    ENT findById(Long id);

    DataTableResponse<ENT> findAll(DataTableRequest request);

    long count();
}
