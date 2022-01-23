package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.entity.Operation;

public interface OperationDao extends BaseDao<Operation>{

    Operation getLastRecord();
}
