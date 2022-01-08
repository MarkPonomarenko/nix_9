package ua.com.alevel.service;


import ua.com.alevel.persistence.entity.Course;
import ua.com.alevel.persistence.entity.Student;

import java.util.List;
import java.util.Set;

public interface CourseService extends BaseService<Course> {

    Set<Student> findCourseStudents(Long id);

    void assignStudent(Long courseId, Long studentId);

    void unassignStudent(Long courseId, Long studentId);

    List<Course> findAll();
}
