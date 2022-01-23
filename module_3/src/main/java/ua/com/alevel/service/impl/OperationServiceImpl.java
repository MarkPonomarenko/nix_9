package ua.com.alevel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.dao.OperationDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.Operation;
import ua.com.alevel.service.OperationService;

@Service
public class OperationServiceImpl implements OperationService {

    private static final Logger LOGGER = LoggerFactory.getLogger("info");

    private final OperationDao operationDao;

    public OperationServiceImpl(OperationDao operationDao) {
        this.operationDao = operationDao;
    }

    @Override
    public void create(Operation entity) {
        operationDao.create(entity);
        LOGGER.info("operation created");
    }

    @Override
    public void update(Operation entity) {
        if (!operationDao.existById(entity.getId())) {
            LOGGER.info("operation " + entity.getId() + " not found for update");
            throw new EntityNotFoundException("operation not found");
        }
        operationDao.update(entity);
        LOGGER.info("operation " + entity.getId() + " updated");
    }

    @Override
    public void delete(Long id) {
        if (!operationDao.existById(id)) {
            LOGGER.info("operation " + id + " not found for delete");
            throw new EntityNotFoundException("operation not found");
        }
        operationDao.delete(id);
        LOGGER.info("operation " + id + " deleted");
    }

    @Override
    public Operation findById(Long id) {
        if (!operationDao.existById(id)) {
            LOGGER.info("operation " + id + " not found");
            throw new EntityNotFoundException("operation not found");
        }
        LOGGER.info("operation " + id + " found");
        return operationDao.findById(id);
    }

    @Override
    public DataTableResponse<Operation> findAll(DataTableRequest request) {
        LOGGER.info("all operations found (DataTableResponse)");
        return operationDao.findAll(request);
    }

    @Override
    public Operation getLastRecord() {
        LOGGER.info("get last operation recorded");
        return operationDao.getLastRecord();
    }
}
