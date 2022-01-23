package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.Operation;

public interface OperationService extends BaseService<Operation>{

    Operation getLastRecord();

}
