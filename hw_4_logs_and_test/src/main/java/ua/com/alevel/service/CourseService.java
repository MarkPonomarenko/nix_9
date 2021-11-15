package ua.com.alevel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.CourseDAO;
import ua.com.alevel.dao.StudentDAO;
import ua.com.alevel.entity.Student;
import ua.com.alevel.entity.Course;
import ua.com.alevel.util.CustomArray;

public class CourseService {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");
    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("info");

    private final CourseDAO courseDAO = new CourseDAO();
    private final StudentDAO studentDAO = new StudentDAO();

    public void create(Course course) {
        LOGGER_INFO.info("course creation");
        courseDAO.create(course);

        Course tmpCourse = new Course();
        tmpCourse.setId(course.getId());
        tmpCourse.setTitle(course.getTitle());
        tmpCourse.setTeacher(course.getTeacher());
        CustomArray<Course> tmpArr = new CustomArray<>();
        tmpArr.add(tmpCourse);

        if (course.getStudents() != null) {
            for (int i = 0; i < course.getStudents().size(); ++i) {
                course.getStudents().getAt(i).setCourses(tmpArr);
                Student current = course.getStudents().getAt(i);
                if (studentDAO.findById(current.getId()) == null) {
                    studentDAO.create(current);
                    LOGGER_INFO.info("needed student created : " + current.getId());
                }
            }
        }

        LOGGER_INFO.info("course created : " + course.getId());
    }

    public void update(Course course) {
        LOGGER_INFO.info("course update : " + course.getId());
        if (courseDAO.findById(course.getId()) != null) {
            courseDAO.update(course);

            String cId = course.getId();
            course.setStudents(courseDAO.findById(cId).getStudents());
            Course current = courseDAO.findById(cId);
            CustomArray<Student> currentStudents = current.getStudents();
            if (currentStudents != null) {
                for (int i = 0; i < currentStudents.size(); ++i) {
                    String sId = currentStudents.getAt(i).getId();
                    for (int j = 0; j < studentDAO.findById(sId).getCourses().size(); ++j) {
                        if (studentDAO.findById(sId).getCourses().getAt(j).getId().equals(cId))
                            studentDAO.findById(sId).getCourses().setAt(course, j);
                    }
                }
            }
            LOGGER_INFO.info("updated successfully");
        } else {
            LOGGER_WARN.warn("course's id not found");
        }
    }

    public void delete(String id) {
        LOGGER_INFO.info("course deletion via id started : " + id);
        Course current = courseDAO.findById(id);
        if (current != null) {
            CustomArray<Student> currentStudents = current.getStudents();
            if (currentStudents != null) {
                for (int i = 0; i < currentStudents.size(); ++i) {
                    String sId = currentStudents.getAt(i).getId();
                    for (int j = 0; j < studentDAO.findById(sId).getCourses().size(); ++j) {
                        if (studentDAO.findById(sId).getCourses().getAt(j).getId().equals(id))
                            studentDAO.findById(sId).getCourses().removeAt(j);
                    }
                }
            }
            courseDAO.delete(id);
        } else {
            LOGGER_WARN.warn("course's id not found");
        }
    }

    public Course findById(String id) {
        LOGGER_INFO.info("find course by id : " + id);
        if (courseDAO.findById(id) == null) {
            System.out.println("Нет такого курса");
            LOGGER_ERROR.error("no such course");
            return null;
        } else {
            LOGGER_INFO.info("course found");
            return courseDAO.findById(id);
        }
    }

    public CustomArray<Course> findAll() {
        LOGGER_INFO.info("finding all courses");
        if (courseDAO.findAll() == null) {
            LOGGER_ERROR.error("array = null");
            System.out.println("Список курсов пуст");
            return null;
        } else {
            LOGGER_INFO.info("done");
            return courseDAO.findAll();
        }
    }

    public CustomArray<Student> findCourseStudents(String id) {
        if (courseDAO.findById(id) != null) {
            LOGGER_INFO.info("finding all students on course via id : " + id);
            return courseDAO.findById(id).getStudents();
        } else {
            LOGGER_ERROR.error("course = null");
            System.out.println("Нет такого  курса");
            return null;
        }
    }
}
