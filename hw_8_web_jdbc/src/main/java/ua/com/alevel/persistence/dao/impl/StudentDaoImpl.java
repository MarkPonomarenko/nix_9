package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.persistence.dao.StudentDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Student;
import ua.com.alevel.util.customset.StudentResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static ua.com.alevel.util.query.SqlQuery.*;

@Service
public class StudentDaoImpl implements StudentDao {

    private final JpaConfig jpaConfig;

    public StudentDaoImpl(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }


    @Override
    public void create(Student student) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_STUDENT)) {
            preparedStatement.setTimestamp(1, new Timestamp(student.getCreated().getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(student.getUpdated().getTime()));
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setString(4, student.getFirstName());
            preparedStatement.setInt(5, student.getAge());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public void update(Student student) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UPDATE_STUDENT_BY_ID + student.getId())) {
            preparedStatement.setString(1, student.getLastName());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(DELETE_STUDENT_BY_ID + id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public boolean existById(Long id) {
        long counter = 0;
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(EXIST_STUDENT_BY_ID + id)) {
            while (resultSet.next()) {
                counter = resultSet.getLong("COUNT(*)");
            }
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
        return counter == 1;
    }

    @Override
    public Student findById(Long id) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_STUDENT_BY_ID + id)) {
            while (resultSet.next()) {
                return resultSetToStudent(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
        return new Student();
    }

    @Override
    public DataTableResponse<Student> findAll(DataTableRequest request) {
        List<Student> students = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int pageLimit = (request.getCurrentPage() - 1) * request.getPageSize();

        String query = FIND_ALL_ACCOUNTINGS_BY_STUDENT_ID +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                pageLimit + "," +
                request.getPageSize();

        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(query)) {
            while (resultSet.next()) {
                StudentResult studentResult = ResultSetToCustomCourse(resultSet);
                students.add(studentResult.getStudent());
                otherParamMap.put(studentResult.getStudent().getId(), studentResult.getCourseCount());
            }
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
        DataTableResponse<Student> responseTable = new DataTableResponse<>();
        responseTable.setItems(students);
        responseTable.setOtherParamMap(otherParamMap);
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

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_ALL_STUDENTS)) {
            while (resultSet.next()) {
                students.add(resultSetToStudent(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
        return students;
    }

    private Student resultSetToStudent(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getLong("id"));
        student.setCreated(resultSet.getTimestamp("created"));
        student.setUpdated(resultSet.getTimestamp("updated"));
        student.setLastName(resultSet.getString("last_name"));
        student.setFirstName(resultSet.getString("first_name"));
        student.setAge(resultSet.getInt("age"));

        return student;
    }

    private StudentResult ResultSetToCustomCourse(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getLong("id"));
        student.setCreated(resultSet.getTimestamp("created"));
        student.setUpdated(resultSet.getTimestamp("updated"));
        student.setLastName(resultSet.getString("last_name"));
        student.setFirstName(resultSet.getString("first_name"));
        student.setAge(resultSet.getInt("age"));
        int courseCounter = resultSet.getInt("courses");
        return new StudentResult(student, courseCounter);
    }
}
