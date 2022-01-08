package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Course;
import ua.com.alevel.persistence.entity.Student;
import ua.com.alevel.persistence.repository.CourseRepository;
import ua.com.alevel.persistence.repository.StudentRepository;
import ua.com.alevel.service.StudentService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {

    private final CrudRepositoryHelper<Course, CourseRepository> courseRepositoryHelper;
    private final CrudRepositoryHelper<Student , StudentRepository> studentRepositoryHelper;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public StudentServiceImpl(CrudRepositoryHelper<Course, CourseRepository> courseRepositoryHelper,
                              CrudRepositoryHelper<Student, StudentRepository> studentRepositoryHelper,
                              CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepositoryHelper = courseRepositoryHelper;
        this.studentRepositoryHelper = studentRepositoryHelper;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void create(Student student) {
        studentRepositoryHelper.create(studentRepository, student);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(Student student) {
        studentRepositoryHelper.update(studentRepository, student);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(Long id) {
        studentRepositoryHelper.delete(studentRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findById(Long id) {
        return studentRepositoryHelper.findById(studentRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<Student> findAll(DataTableRequest request) {
        return studentRepositoryHelper.findAll(studentRepository, request);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Set<Course> findStudentCourses(Long id) {
        return studentRepositoryHelper.findById(studentRepository, id).get().getCourses();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void assignCourse(Long courseId, Long studentId) {
        Course course = courseRepositoryHelper.findById(courseRepository, courseId).get();
        Student student = studentRepositoryHelper.findById(studentRepository, studentId).get();
        student.addCourse(course);
        studentRepositoryHelper.update(studentRepository, student);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void unassignCourse(Long courseId, Long studentId) {
        Course course = courseRepositoryHelper.findById(courseRepository, courseId).get();
        Student student = studentRepositoryHelper.findById(studentRepository, studentId).get();
        student.removeCourse(course);
        studentRepositoryHelper.update(studentRepository, student);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }
}
