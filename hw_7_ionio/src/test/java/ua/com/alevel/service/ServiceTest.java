package ua.com.alevel.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.com.alevel.entity.Course;
import ua.com.alevel.entity.Student;
import ua.com.alevel.service.impl.CourseServiceImpl;
import ua.com.alevel.service.impl.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class ServiceTest {

    private final StudentServiceImpl studentService = new StudentServiceImpl();
    private final CourseServiceImpl courseService = new CourseServiceImpl();

    @Test
    public void shouldTestService() {
        //create
        Student tmp = new Student();
        tmp.setFirstName("Name1");
        tmp.setLastName("Last1");
        tmp.setAge(10);
        tmp.setCourses(new ArrayList<>());
        studentService.create(tmp);
        Assertions.assertEquals(1, studentService.findAll().size());
        Course tmp1 = new Course();
        tmp1.setCredits(20);
        tmp1.setTeacher("Teacher1");
        tmp1.setTitle("Title1");
        tmp1.setStudents(new ArrayList<>());
        courseService.create(tmp1);
        Assertions.assertEquals(1, courseService.findAll().size());

        //update
        Student upd = new Student();
        String idForTest = studentService.findAll().get(0).getId();
        upd.setId(studentService.findAll().get(0).getId());
        upd.setFirstName("NameUpd");
        upd.setLastName("LastUpd");
        upd.setAge(10);
        List<String> courses = new ArrayList<>();
        courses.add(courseService.findAll().get(0).getId());
        upd.setCourses(courses);
        studentService.update(upd);
        Assertions.assertEquals("NameUpd", studentService.findAll().get(0).getFirstName());
        Assertions.assertEquals(1, courseService.findAll().get(0).getStudents().size());

        //findById
        Assertions.assertEquals("NameUpd", studentService.findById(idForTest).getFirstName());

        //delete
        studentService.delete(idForTest);
        Assertions.assertEquals(0, studentService.findAll().size());
        Assertions.assertEquals("",courseService.findAll().get(0).getStudents().get(0));
    }

    @Test
    public void shouldCleanFiles() {
        studentService.deleteAll();
        courseService.deleteAll();
        Assertions.assertEquals(0, studentService.findAll().size());
        Assertions.assertEquals(0, courseService.findAll().size());
    }
}
