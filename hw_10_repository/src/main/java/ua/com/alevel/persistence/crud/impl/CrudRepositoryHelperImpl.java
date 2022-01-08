package ua.com.alevel.persistence.crud.impl;

import org.apache.commons.collections4.MapUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.repository.AbstractRepository;

import java.util.Optional;

@Service
public class CrudRepositoryHelperImpl<
        ENT extends BaseEntity,
        REP extends AbstractRepository<ENT>>
        implements CrudRepositoryHelper<ENT, REP> {

    @Override
    public void create(REP repository, ENT entity) {
        repository.save(entity);
    }

    @Override
    public void update(REP repository, ENT entity) {
        exists(repository, entity.getId());
        repository.save(entity);
    }

    @Override
    public void delete(REP repository, Long id) {
        exists(repository, id);
        repository.deleteById(id);
    }

    @Override
    public Optional<ENT> findById(REP repository, Long id) {
        return repository.findById(id);
    }

    @Override
    public DataTableResponse<ENT> findAll(REP repository, DataTableRequest dataTableRequest) {
        int page = dataTableRequest.getPage() - 1;
        int size = dataTableRequest.getSize();
        String sortParam = dataTableRequest.getSort();
        String orderParam = dataTableRequest.getOrder();

        Sort sort = orderParam.equals("desc")
                ? Sort.by(sortParam).descending()
                : Sort.by(sortParam).ascending();

        if (MapUtils.isNotEmpty(dataTableRequest.getRequestParamMap())) {
            System.out.println("dataTableRequest = " + dataTableRequest.getRequestParamMap());
        }

        PageRequest request = PageRequest.of(page, size, sort);

        Page<ENT> pageEntity = repository.findAll(request);

        DataTableResponse<ENT> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setSort(sortParam);
        dataTableResponse.setOrder(orderParam);
        dataTableResponse.setPageSize(size);
        dataTableResponse.setCurrentPage(page);
        dataTableResponse.setItemsSize(pageEntity.getTotalElements());
        dataTableResponse.setTotalPageSize(pageEntity.getTotalPages());
        dataTableResponse.setItems(pageEntity.getContent());

        return dataTableResponse;
    }

    private void exists(REP repository, Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("entity not found");
        }
    }
}
