package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.AccountingDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Accounting;
import ua.com.alevel.service.AccountingService;

import java.util.List;

@Service
public class AccountingServiceImpl implements AccountingService {

    private final AccountingDao accountingDao;

    public AccountingServiceImpl(AccountingDao accountingDao) {
        this.accountingDao = accountingDao;
    }

    @Override
    public List<Accounting> findCourseStudents(Long courseId) {
        return accountingDao.findCourseStudents(courseId);
    }

    @Override
    public List<Accounting> findStudentCourses(Long studentId) {
        return accountingDao.findStudentCourses(studentId);
    }

    @Override
    public Accounting findByBothId(Long courseId, Long studentId) {
        return accountingDao.findByBothId(courseId, studentId);
    }

    @Override
    public void create(Accounting accounting) {
        accountingDao.create(accounting);
    }

    @Override
    public void update(Accounting accounting) {
        accountingDao.update(accounting);
    }

    @Override
    public void delete(Long id) {
        accountingDao.delete(id);
    }

    @Override
    public Accounting findById(Long id) {
        return accountingDao.findById(id);
    }

    @Override
    public DataTableResponse<Accounting> findAll(DataTableRequest request) {
        DataTableResponse<Accounting> dataTableResponse = accountingDao.findAll(request);
        dataTableResponse.setItemsSize(accountingDao.count());
        return dataTableResponse;
    }
}
