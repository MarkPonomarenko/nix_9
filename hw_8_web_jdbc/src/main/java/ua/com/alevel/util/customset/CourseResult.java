package ua.com.alevel.util.customset;

import ua.com.alevel.persistence.entity.Course;

public class CourseResult {

    private final Course course;
    private final int studentCount;


    public CourseResult(Course course, int studentCount) {
        this.course = course;
        this.studentCount = studentCount;
    }

    public Course getCourse() {
        return course;
    }

    public int getStudentCount() {
        return studentCount;
    }
}
