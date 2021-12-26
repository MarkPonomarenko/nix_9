package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.persistence.dao.AccountingDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Accounting;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static ua.com.alevel.util.query.JpaQuery.*;

@Service
public class AccountingDaoImpl implements AccountingDao {

    private final JpaConfig jpaConfig;

    public AccountingDaoImpl(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }


    @Override
    public void create(Accounting accounting) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_ACCOUNTING)){
            preparedStatement.setLong(1, accounting.getCourseId());
            preparedStatement.setLong(2, accounting.getStudentId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public void update(Accounting accounting) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UPDATE_STUDENT_BY_ID + accounting.getId())){
            preparedStatement.setLong(1,accounting.getCourseId());
            preparedStatement.setLong(2, accounting.getStudentId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(DELETE_ACCOUNTING_BY_ID + id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public boolean existById(Long id) {
        long counter = 0;
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(EXIST_ACCOUNTING_BY_ID + id)){
            while (resultSet.next()) {
                counter = resultSet.getLong("COUNT(*)");
            }
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
        return counter == 1;
    }

    @Override
    public Accounting findById(Long id) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_ACCOUNTING_BY_ID + id)){
            while (resultSet.next()) {
                return resultSetToAccounting(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
        return null;
    }

    @Override
    public DataTableResponse<Accounting> findAll(DataTableRequest request) {
        List<Accounting> accountings = new ArrayList<>();
        int pageLimit = (request.getCurrentPage() - 1) * request.getPageSize();

        String query = FIND_ALL_ACCOUNTINGS_BY_STUDENT_ID +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                pageLimit + "," +
                request.getPageSize();

        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(query)){
            while (resultSet.next()) {
                Accounting accounting = resultSetToAccounting(resultSet);
                accountings.add(accounting);
            }
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
        DataTableResponse<Accounting> responseTable = new DataTableResponse<>();
        responseTable.setItems(accountings);
        return responseTable;
    }

    @Override
    public long count() {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(STUDENT_COUNT)) {
            while (resultSet.next()) {
                return resultSet.getLong("counter");
            }
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
        return 0;
    }

    private Accounting resultSetToAccounting(ResultSet resultSet) throws SQLException {

        Accounting accounting = new Accounting();
        accounting.setId(resultSet.getLong("id"));
        accounting.setCourseId(resultSet.getLong("course_id"));
        accounting.setStudentId(resultSet.getLong("student_id"));
        return accounting;
    }

    @Override
    public List<Accounting> findCourseStudents(Long courseId) {
        List<Accounting> accountings = new ArrayList<>();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_STUDENTS_BY_COURSE_ID + courseId)){
            while(resultSet.next()) {
                accountings.add(resultSetToAccounting(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("e10 = " + e.getMessage());
        }
        return accountings;
    }

    @Override
    public List<Accounting> findStudentCourses(Long studentId) {
        List<Accounting> accountings = new ArrayList<>();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_COURSES_BY_STUDENT_ID + studentId)){
            while(resultSet.next()) {
                accountings.add(resultSetToAccounting(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return accountings;
    }

    @Override
    public Accounting findByBothId(Long courseId, Long studentId) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(FIND_ACCOUNTING_BY_COURSE_STUDENT_IDS)){
            preparedStatement.setLong(1, courseId);
            preparedStatement.setLong(2, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                return resultSetToAccounting(resultSet);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
