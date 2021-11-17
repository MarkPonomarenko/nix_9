package service;

import org.junit.jupiter.api.*;
import ua.com.alevel.entity.Course;
import ua.com.alevel.entity.Student;
import ua.com.alevel.service.CourseService;
import ua.com.alevel.service.StudentService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestArray {

    private final static StudentService studentService = new StudentService();
    private final static CourseService courseService = new CourseService();
    private final static int SIZE = 10;

    @BeforeAll
    public static void setUp() {
        for (int i = 0; i < SIZE; ++i) {
            Student student = StudentGenerationUtil.generateStudent(StudentGenerationUtil.NAME + i);
            studentService.create(student);
        }
        for (int i = 0; i < SIZE / 2; ++i) {
            Course course = CourseGenerationUtil.generateCourse(CourseGenerationUtil.TITLE + i);
            courseService.create(course);
        }
        Assertions.assertEquals(SIZE / 2, courseService.findAll().size());
        Assertions.assertEquals(SIZE, studentService.findAll().size());
    }

    @Order(1)
    @Test
    public void shouldCreateBoth() {
        Student student = StudentGenerationUtil.generateStudent("test_Name");
        studentService.create(student);
        Course course = CourseGenerationUtil.generateCourse("test_Title");
        courseService.create(course);
        Assertions.assertEquals(SIZE + 1, studentService.findAll().size());
        Assertions.assertEquals((SIZE / 2) + 1, courseService.findAll().size());
    }

    @Order(2)
    @Test
    public void shouldDeleteBoth() {
        studentService.delete(studentService.findAll().getAt(0).getId());
        courseService.delete(courseService.findAll().getAt(0).getId());
        Assertions.assertEquals(SIZE, studentService.findAll().size());
        Assertions.assertEquals(SIZE / 2, courseService.findAll().size());
    }

    @Order(3)
    @Test
    public void shouldFindAll() {
        Assertions.assertEquals(SIZE, studentService.findAll().size());
        Assertions.assertEquals(SIZE / 2, courseService.findAll().size());
    }
}
