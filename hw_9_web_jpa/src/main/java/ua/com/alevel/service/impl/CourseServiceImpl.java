package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.dao.CourseDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Course;
import ua.com.alevel.persistence.entity.Student;
import ua.com.alevel.service.CourseService;

import java.util.List;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseDao courseDao;

    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public void create(Course course) {
        courseDao.create(course);
    }

    @Override
    public void update(Course course) {
        if (!courseDao.existById(course.getId())) {
            throw new EntityNotFoundException("course not found");
        }
        courseDao.update(course);
    }

    @Override
    public void delete(Long id) {
        if (!courseDao.existById(id)) {
            throw new EntityNotFoundException("course not found");
        }
        courseDao.delete(id);
    }

    @Override
    public Course findById(Long id) {
        if (!courseDao.existById(id)) {
            throw new EntityNotFoundException("course not found");
        }
        return courseDao.findById(id);
    }

    @Override
    public List<Course> findAll() {
        return courseDao.findAll();
    }

    @Override
    public DataTableResponse<Course> findAll(DataTableRequest request) {
        return courseDao.findAll(request);
    }

    @Override
    public Set<Student> findCourseStudents(Long id) {
        return courseDao.findCourseStudents(id);
    }

    @Override
    public void assignStudent(Long courseId, Long studentId) {
        courseDao.assignStudent(courseId, studentId);
    }

    @Override
    public void unassignStudent(Long courseId, Long studentId) {
        courseDao.unassignStudent(courseId, studentId);
    }
}
