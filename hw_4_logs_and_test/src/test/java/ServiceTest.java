
import org.junit.jupiter.api.*;
import ua.com.alevel.entity.Course;
import ua.com.alevel.entity.Student;
import ua.com.alevel.service.CourseService;
import ua.com.alevel.service.StudentService;
import ua.com.alevel.util.CustomArray;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceTest {

    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();

    @Order(1)
    @Test
    public void shouldBeStudentWithCourses() {
        Course firstCourse = CourseGenerationUtil.generateCourse();
        Course secondCourse = CourseGenerationUtil.generateCourse("Title", "Egor");
        Course thirdCourse = CourseGenerationUtil.generateCourse("Title");

        Student student = StudentGenerationUtil.generateStudent("name");
        courseService.create(firstCourse);
        courseService.create(secondCourse);
        CustomArray<Course> courses = new CustomArray<>();
        courses.add(firstCourse);
        courses.add(secondCourse);
        student.setCourses(courses);
        courseService.create(thirdCourse);
        studentService.create(student);

        Assertions.assertEquals(3, courseService.findAll().size());
        Assertions.assertEquals(2, studentService.findStudentCourses(student.getId()).size());
    }

    @Order(2)
    @Test
    public void shouldReplaceNullInStudentName() {
        Student student = new Student();
        student.setFirstName(null);
        student.setLastName(StudentGenerationUtil.LAST_NAME);
        student.setAge(StudentGenerationUtil.AGE);
        studentService.create(student);
        verifyStudentsWhenStudentsIsNotEmpty(2);
        Assertions.assertEquals("Unknown", studentService.findById(student.getId()).getFirstName());
    }

    @Order(3)
    @Test
    public void shouldReplcaeNullInCourseTeacher() {
        Course course = new Course();
        course.setTitle("test_title");
        course.setTeacher(null);
        courseService.create(course);
        Assertions.assertNotNull(courseService.findById(course.getId()).getTeacher());
    }

    @Order(4)
    @Test
    public void shouldUpdateCourse() {
        CustomArray<Course> courses = courseService.findAll();
        Course course = new Course();
        course.setId(courses.getAt(1).getId());
        course.setTitle("title_Upd");
        course.setTeacher("teacher_Upd");
        courseService.update(course);

        Assertions.assertEquals("teacher_Upd", courseService.findById(course.getId()).getTeacher());
    }

    @Order(5)
    @Test
    public void shouldFindStudentCourses() {
        CustomArray<Course> studentCourses = studentService.findAll().getAt(0).getCourses();
        Assertions.assertEquals(2, studentCourses.size());
    }

    @Order(6)
    @Test
    public void shouldDeleteCourse() {
        Course course = courseService.findAll().getAt(2);
        courseService.delete(course.getId());
        Assertions.assertEquals(3, courseService.findAll().size());
    }

    @Order(7)
    @Test
    public void shouldFindCourseStudents() {
        CustomArray<Student> students = courseService.findCourseStudents(courseService.findAll().getAt(0).getId());
        Assertions.assertEquals(1, students.size());
    }

    @Order(9)
    @Test
    public void shouldDeleteAll() {
        courseService.findAll().flush();
        studentService.findAll().flush();
        Assertions.assertEquals(0, courseService.findAll().size());
        Assertions.assertEquals(0, studentService.findAll().size());
    }

    private void verifyStudentsWhenStudentsIsNotEmpty(int size) {
        CustomArray<Student> students = studentService.findAll();
        Assertions.assertFalse(students.isEmpty());
        Assertions.assertEquals(size, studentService.findAll().size());
    }
}
