package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.entity.Course;
import ua.com.alevel.persistence.entity.Student;

import java.util.List;
import java.util.Set;

public interface StudentDao extends BaseDao<Student> {
    Set<Course> findCourseStudents(Long id);

    void assignCourse(Long courseId, Long studentId);

    void unassignCourse(Long courseId, Long studentId);
}
