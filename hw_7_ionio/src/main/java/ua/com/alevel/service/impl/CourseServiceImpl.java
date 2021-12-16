package ua.com.alevel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.impl.CourseDaoImpl;
import ua.com.alevel.dao.impl.StudentDaoImpl;
import ua.com.alevel.entity.Course;
import ua.com.alevel.entity.Student;
import ua.com.alevel.service.CourseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CourseServiceImpl implements CourseService {

    private static final CourseDaoImpl courseDao = new CourseDaoImpl();
    private static final StudentServiceImpl studentService = new StudentServiceImpl();
    private static final StudentDaoImpl studentDao = new StudentDaoImpl();

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");

    @Override
    public void create(Course course) {
        LOGGER_INFO.info("[Course] Creating course");
        courseDao.create(course);
        //изменение курсов затронутых созданым студентом
        List<String> ids = course.getStudents();
        if (ids == null) {
            LOGGER_WARN.warn("[Course] -No students to update");
            return;
        }
        for (String id : ids) {
            List<String> studCourses = studentService.findById(id).getCourses();
            if (Objects.equals(studCourses.get(0), "")) {
                studCourses = new ArrayList<>();
            }
            studCourses.add(course.getId());
            Student tmp = studentService.findById(id);
            tmp.setCourses(studCourses);
            studentDao.update(tmp);
        }
        LOGGER_INFO.info("[Course] -Updated students affected by creating student");
    }

    @Override
    public void update(Course course) {
        LOGGER_INFO.info("[Course] Updating course: " + course.getId());
        courseDao.update(course);
        List<String> ids = course.getStudents();
        List<Student> students = studentService.findAll();
        if (ids == null) {
            LOGGER_WARN.warn("[Course] -No students to update");
            return;
        }
        for (Student student : students) {
            List<String> arrs = student.getCourses();

            //удаляем из студентов где курса больше нет
            if (!ids.contains(student.getId())) {
                if (arrs.contains(course.getId())) {
                    arrs.remove(course.getId());
                    student.setCourses(arrs);
                    studentDao.update(student);
                }
            }
            //добавляем туда где появился
            else {
                if (!arrs.contains(course.getId())) {
                    if (Objects.equals(arrs.get(0), "")) {
                        arrs = new ArrayList<>();
                    }
                    arrs.add(course.getId());
                    student.setCourses(arrs);
                    studentDao.update(student);
                }
            }
        }
        LOGGER_INFO.info("[Course] -Updated students affected by updating course");
    }

    @Override
    public void delete(String id) {
        LOGGER_INFO.info("[Course] Deleting course");
        Course tmp = courseDao.findById(id);
        if (tmp == null) {
            LOGGER_WARN.warn("[Course] -No courses to delete");
            System.out.println("Курс не найден");
            return;
        }
        courseDao.delete(id);
        List<String> students = tmp.getStudents();
        List<Student> allStudents = studentService.findAll();
        for (Student student : allStudents) {
            if (students.contains(student.getId())) {
                List<String> studentCourses = student.getCourses();
                studentCourses.remove(id);
                student.setCourses(studentCourses);
                studentDao.update(student);
            }
        }
        LOGGER_INFO.info("[Course] -Updated students affected by deleting course");
    }

    @Override
    public void deleteAll() {
        LOGGER_INFO.info("[Course] Deleting all courses");
        courseDao.deleteAll();
        List<Student> students = studentService.findAll();
        for (Student student : students) {
            List<String> tmp = student.getCourses();
            tmp.clear();
            student.setCourses(tmp);
            studentService.update(student);
        }
        LOGGER_INFO.info("[Course] -Updating affected students");
    }

    @Override
    public Course findById(String id) {
        LOGGER_INFO.info("[Course] Finding by id: " + id);
        return courseDao.findById(id);
    }

    @Override
    public List<Course> findAll() {
        LOGGER_INFO.info("[Course] Finding all");
        return courseDao.findAll();
    }
}
