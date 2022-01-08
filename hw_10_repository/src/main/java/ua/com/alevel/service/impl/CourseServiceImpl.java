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
import ua.com.alevel.service.CourseService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {

    private final CrudRepositoryHelper<Course, CourseRepository> courseRepositoryHelper;
    private final CrudRepositoryHelper<Student, StudentRepository> studentRepositoryHelper;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseServiceImpl(CrudRepositoryHelper<Course, CourseRepository> courseRepositoryHelper,
                             CrudRepositoryHelper<Student, StudentRepository> studentRepositoryHelper,
                             CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepositoryHelper = courseRepositoryHelper;
        this.studentRepositoryHelper = studentRepositoryHelper;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void create(Course course) {
        courseRepositoryHelper.create(courseRepository, course);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(Course course) {
        courseRepositoryHelper.update(courseRepository, course);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(Long id) {
        courseRepositoryHelper.delete(courseRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return courseRepositoryHelper.findById(courseRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<Course> findAll(DataTableRequest request) {
        return courseRepositoryHelper.findAll(courseRepository, request);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Set<Student> findCourseStudents(Long id) {
        return courseRepositoryHelper.findById(courseRepository, id).get().getStudents();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void assignStudent(Long courseId, Long studentId) {
        Course course = courseRepositoryHelper.findById(courseRepository, courseId).get();
        Student student = studentRepositoryHelper.findById(studentRepository, studentId).get();
        course.addStudent(student);
        courseRepositoryHelper.update(courseRepository, course);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void unassignStudent(Long courseId, Long studentId) {
        Course course = courseRepositoryHelper.findById(courseRepository, courseId).get();
        Student student = studentRepositoryHelper.findById(studentRepository, studentId).get();
        course.removeStudent(student);
        courseRepositoryHelper.update(courseRepository, course);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }
}
