package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.persistence.dao.CourseDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Course;
import ua.com.alevel.util.customset.CourseResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static ua.com.alevel.util.query.JpaQuery.*;

@Service
public class CourseDaoImpl implements CourseDao {

    private final JpaConfig jpaConfig;

    public CourseDaoImpl(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }


    @Override
    public void create(Course course) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_COURSE)){
            preparedStatement.setTimestamp(1, new Timestamp(course.getCreated().getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(course.getUpdated().getTime()));
            preparedStatement.setString(3, course.getTitle());
            preparedStatement.setString(4, course.getTitle());
            preparedStatement.setInt(5, course.getCredits());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("e1 = " + e.getMessage());
        }
    }

    @Override
    public void update(Course course) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UPDATE_COURSE_BY_ID + course.getId())){
            preparedStatement.setString(1, course.getTitle());
            preparedStatement.setString(2, course.getTeacher());
            preparedStatement.setInt(3, course.getCredits());
            preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("e2 = " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(DELETE_COURSE_BY_ID + id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("e3 = " + e.getMessage());
        }
    }

    @Override
    public boolean existById(Long id) {
        long counter = 0;
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(EXIST_COURSE_BY_ID + id)){
            while (resultSet.next()) {
                counter = resultSet.getLong("COUNT(*)");
            }
        } catch (SQLException e) {
            System.out.println("e4 = " + e.getMessage());
        }
        return counter == 1;
    }

    @Override
    public Course findById(Long id) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_COURSE_BY_ID + id)){
            while (resultSet.next()) {
                return resultSetToCourse(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("e5 = " + e.getMessage());
        }
        return new Course();
    }

    @Override
    public DataTableResponse<Course> findAll(DataTableRequest request) {
        List<Course> courses = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int pageLimit = (request.getCurrentPage() - 1) * request.getPageSize();

        String query = FIND_ALL_ACCOUNTINGS_BY_COURSE_ID +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                pageLimit + "," +
                request.getPageSize();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(query)){
            while (resultSet.next()) {
                CourseResult courseResult = ResultSetToCustomCourse(resultSet);
                courses.add(courseResult.getCourse());
                otherParamMap.put(courseResult.getCourse().getId(), courseResult.getStudentCount());
            }
        } catch (SQLException e) {
            System.out.println("e6 = " + e.getMessage());
        }
        DataTableResponse<Course> responseTable = new DataTableResponse<>();
        responseTable.setItems(courses);
        responseTable.setOtherParamMap(otherParamMap);
        return responseTable;
    }

    @Override
    public long count() {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(COURSE_COUNT)) {
            while (resultSet.next()) {
                return resultSet.getLong("counter");
            }
        } catch (SQLException e) {
            System.out.println("e7 = " + e.getMessage());
        }
        return 0;
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_ALL_COURSES)) {
            while (resultSet.next()) {
                courses.add(resultSetToCourse(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("e8 = " + e.getMessage());
        }
        return courses;
    }

    private Course resultSetToCourse(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getLong("id"));
        course.setCreated(resultSet.getTimestamp("created"));
        course.setUpdated(resultSet.getTimestamp("updated"));
        course.setTitle(resultSet.getString("title"));
        course.setTeacher(resultSet.getString("teacher"));
        course.setCredits(resultSet.getInt("credits"));

        return course;
    }

    private CourseResult ResultSetToCustomCourse(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getLong("id"));
        course.setCreated(resultSet.getTimestamp("created"));
        course.setUpdated(resultSet.getTimestamp("updated"));
        course.setTitle(resultSet.getString("title"));
        course.setTeacher(resultSet.getString("teacher"));
        course.setCredits(resultSet.getInt("credits"));
        int studentCounter = resultSet.getInt("students");
        return new CourseResult(course, studentCounter);
    }
}
