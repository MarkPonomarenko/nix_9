package ua.com.alevel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.impl.CourseDaoImpl;
import ua.com.alevel.dao.impl.StudentDaoImpl;
import ua.com.alevel.entity.Course;
import ua.com.alevel.entity.Student;
import ua.com.alevel.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentServiceImpl implements StudentService {

    private static final StudentDaoImpl studentDao = new StudentDaoImpl();
    private static final CourseServiceImpl courseService = new CourseServiceImpl();
    private static final CourseDaoImpl courseDao = new CourseDaoImpl();

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");

    @Override
    public void create(Student student) {
        LOGGER_INFO.info("[Student] Creating student");
        studentDao.create(student);
        //изменение курсов затронутых созданым студентом
        List<String> ids = student.getCourses();
        if (ids == null) {
            LOGGER_WARN.warn("[Student] -No courses to update");
            return;
        }
        for (String id : ids) {
            List<String> courseStuds = courseService.findById(id).getStudents();
            if (Objects.equals(courseStuds.get(0), "")) {
                courseStuds = new ArrayList<>();
            }
            courseStuds.add(student.getId());
            Course tmp = courseService.findById(id);
            tmp.setStudents(courseStuds);
            courseDao.update(tmp);
        }
        LOGGER_INFO.info("[Student] -Updated courses affected by creating student");
    }

    @Override
    public void update(Student student) {
        LOGGER_INFO.info("[Student] Updating student: " + student.getId());
        studentDao.update(student);
        List<String> ids = student.getCourses();
        List<Course> courses = courseService.findAll();
        if (ids == null) {
            LOGGER_WARN.warn("[Student] -No courses to update");
            return;
        }
        boolean contains;
        for (Course course : courses) {
            List<String> arrs = course.getStudents();

            //удаляем из курсов где студента больше нет
            if (!ids.contains(course.getId())) {
                if (arrs.contains(student.getId())) {
                    arrs.remove(student.getId());
                    course.setStudents(arrs);
                    courseDao.update(course);
                }
            }
            //добавляем туда где появился
            else {
                if (!arrs.contains(student.getId())) {
                    if (Objects.equals(arrs.get(0), "")) {
                        arrs = new ArrayList<>();
                    }
                    arrs.add(student.getId());
                    course.setStudents(arrs);
                    courseDao.update(course);
                }
            }
        }
        LOGGER_INFO.info("[Student] -Updated courses affected by updating student");
    }

    @Override
    public void delete(String id) {
        LOGGER_INFO.info("[Student] Deleting student");
        Student tmp = studentDao.findById(id);
        if (tmp == null) {
            LOGGER_WARN.warn("[Student] -No student to delete");
            System.out.println("Студент не найден");
            return;
        }
        studentDao.delete(id);
        List<String> courses = tmp.getCourses();
        List<Course> allCourses = courseService.findAll();
        for (Course course : allCourses) {
            if (courses.contains(course.getId())) {
                List<String> courseStudents = course.getStudents();
                courseStudents.remove(id);
                course.setStudents(courseStudents);
                courseDao.update(course);
            }
        }
        LOGGER_INFO.info("[Student] -Updated courses affected by deleting student");
    }

    @Override
    public void deleteAll() {
        LOGGER_INFO.info("[Student] Deleting all students");
        studentDao.deleteAll();
        List<Course> courses = courseService.findAll();
        for (Course course : courses) {
            List<String> tmp = course.getStudents();
            tmp.clear();
            course.setStudents(tmp);
            courseService.update(course);
        }
        LOGGER_INFO.info("[Student] -Updating affected courses");
    }

    @Override
    public Student findById(String id) {
        LOGGER_INFO.info("[Student] Finding by id: " + id);
        return studentDao.findById(id);
    }

    @Override
    public List<Student> findAll() {
        LOGGER_INFO.info("[Student] Finding all");
        return studentDao.findAll();
    }
}
