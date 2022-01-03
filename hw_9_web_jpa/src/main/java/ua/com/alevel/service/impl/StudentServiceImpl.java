package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.dao.StudentDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Course;
import ua.com.alevel.persistence.entity.Student;
import ua.com.alevel.service.StudentService;

import java.util.List;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public void create(Student student) {
        studentDao.create(student);
    }

    @Override
    public void update(Student student) {
        if (!studentDao.existById(student.getId())) {
            throw new EntityNotFoundException("student not found");
        }
        studentDao.update(student);
    }

    @Override
    public void delete(Long id) {
        if (!studentDao.existById(id)) {
            throw new EntityNotFoundException("student not found");
        }
        studentDao.delete(id);
    }

    @Override
    public Student findById(Long id) {
        if (!studentDao.existById(id)) {
            throw new EntityNotFoundException("student not found");
        }
        return studentDao.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public DataTableResponse<Student> findAll(DataTableRequest request) {
        return studentDao.findAll(request);
    }

    @Override
    public Set<Course> findCourseStudents(Long id) {
        return studentDao.findCourseStudents(id);
    }

    @Override
    public void assignCourse(Long courseId, Long studentId) {
        studentDao.assignCourse(courseId, studentId);
    }

    @Override
    public void unassignCourse(Long courseId, Long studentId) {
        studentDao.unassignCourse(courseId, studentId);
    }
}
