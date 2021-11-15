package ua.com.alevel.service;


import ua.com.alevel.dao.CourseDAO;
import ua.com.alevel.dao.StudentDAO;
import ua.com.alevel.entity.Course;
import ua.com.alevel.entity.Student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.util.CustomArray;

import java.util.Arrays;

public class StudentService {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");
    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");

    private final CourseService courseService = new CourseService();
    private final StudentDAO studentDAO = new StudentDAO();
    private final CourseDAO courseDAO = new CourseDAO();

    public void create(Student student) {
        LOGGER_INFO.info("student creation");
        studentDAO.create(student);

        Student tmpStudent = new Student();
        tmpStudent.setId(student.getId());
        tmpStudent.setFirstName(student.getFirstName());
        tmpStudent.setLastName(student.getLastName());
        tmpStudent.setAge(student.getAge());
        tmpStudent.setCourses(student.getCourses());
        CustomArray<Student> tmpArr = new CustomArray<>();
        tmpArr.add(tmpStudent);

        if (student.getCourses() != null) {
            for (int i = 0; i < student.getCourses().size(); ++i) {
                //добавление нового студента к курсам
                if (courseService.findCourseStudents(student.getCourses().getAt(i).getId()) != null) {
                    CustomArray<Student> nUpd = (courseService.findCourseStudents(student.getCourses().getAt(i).getId()));
                    nUpd.add(tmpStudent);
                    student.getCourses().getAt(i).setStudents(nUpd);
                } else
                    student.getCourses().getAt(i).setStudents(tmpArr);
                Course current = student.getCourses().getAt(i);
                if (courseDAO.findById(current.getId()) == null) {
                    LOGGER_INFO.info("needed course created or updated : " + current.getId());
                    courseDAO.create(current);
                }
            }
        }
        LOGGER_INFO.info("student created : " + student.getId());
    }

    public void update(Student student) {
        LOGGER_INFO.info("student update : " + student.getId());
        if (studentDAO.findById(student.getId()) != null) {
            studentDAO.update(student);

            String sId = student.getId();
            student.setCourses(studentDAO.findById(sId).getCourses());
            Student current = studentDAO.findById(sId);
            if (current.getCourses() != null) {
                boolean changed = false;
                for (int i = 0; i < current.getCourses().size(); ++i) {
                    //флаг для отслеживания есть ли студент на курсе
                    changed = false;
                    String cId = current.getCourses().getAt(i).getId();
                    for (int j = 0; j < courseDAO.findById(cId).getStudents().size(); ++j) {
                        if (courseDAO.findById(cId).getStudents().getAt(j).getId().equals(sId)) {
                            courseDAO.findById(cId).getStudents().setAt(student, j);
                            changed = true;
                        }
                    }
                    //если нет - добавляем
                    if (!changed) {
                        courseDAO.findById(cId).getStudents().add(student);
                    }
                }
            }
            LOGGER_INFO.info("updated successfully");
        } else {
            LOGGER_WARN.warn("student's id not found");
        }
    }

    public void delete(String id) {
        LOGGER_INFO.info("student deletion via id started : " + id);
        Student current = studentDAO.findById(id);
        if (current != null) {
            CustomArray<Course> currentCourses = current.getCourses();
            if (currentCourses != null) {
                for (int i = 0; i < currentCourses.size(); ++i) {
                    String cId = currentCourses.getAt(i).getId();
                    for (int j = 0; j < courseDAO.findById(cId).getStudents().size(); ++j) {
                        if (courseDAO.findById(cId).getStudents().getAt(j).getId().equals(id))
                            courseDAO.findById(cId).getStudents().removeAt(j);
                    }
                }
            }
            studentDAO.delete(id);
        } else {
            LOGGER_WARN.warn("student's id not found");
        }
    }

    public Student findById(String id) {
        LOGGER_INFO.info("find student by id : " + id);
        if (studentDAO.findById(id) == null) {
            System.out.println("Нет такого студента");
            LOGGER_ERROR.error("no such student");
            return null;
        } else {
            LOGGER_INFO.info("student found");
            return studentDAO.findById(id);
        }
    }

    public CustomArray<Student> findAll() {
        LOGGER_INFO.info("finding all students");
        if (studentDAO.findAll() == null) {
            LOGGER_ERROR.error("array = null");
            System.out.println("Список студентов пуст");
            return null;
        } else {
            LOGGER_INFO.info("done");
            return studentDAO.findAll();
        }
    }

    public CustomArray<Course> findStudentCourses(String id) {
        if (studentDAO.findById(id) != null) {
            LOGGER_INFO.info("finding all student's courses via id : " + id);
            return studentDAO.findById(id).getCourses();
        } else {
            LOGGER_ERROR.error("student = null");
            System.out.println("Нет такого студента");
            return null;
        }
    }
}
