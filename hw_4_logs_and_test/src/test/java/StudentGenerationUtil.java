import ua.com.alevel.entity.Student;

public class StudentGenerationUtil {

    public static final String NAME = "test_firstName";
    public static final String LAST_NAME = "test_lastName";
    public static final int AGE = 15;

    public static Student generateStudent() {
        Student student = new Student();
        student.setFirstName(NAME);
        student.setLastName(LAST_NAME);
        student.setAge(AGE);
        return student;
    }

    public static Student generateStudent(String name, int age) {
        Student student = new Student();
        student.setFirstName(name);
        student.setLastName(LAST_NAME);
        student.setAge(age);
        return student;
    }

    public static Student generateStudent(String name) {
        Student student = new Student();
        student.setFirstName(name);
        student.setLastName(LAST_NAME);
        student.setAge(AGE);
        return student;
    }

    public static Student generateStudent(int age) {
        Student student = new Student();
        student.setFirstName(NAME);
        student.setLastName(LAST_NAME);
        student.setAge(age);
        return student;
    }

}
