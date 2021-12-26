package ua.com.alevel.util.customset;

import ua.com.alevel.persistence.entity.Student;

public class StudentResult {

    private final Student student;
    private final int courseCount;


    public StudentResult(Student student, int courseCount) {
        this.student = student;
        this.courseCount = courseCount;
    }

    public Student getStudent() {
        return student;
    }

    public int getCourseCount() {
        return courseCount;
    }
}
