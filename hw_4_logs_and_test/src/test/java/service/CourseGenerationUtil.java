package service;

import ua.com.alevel.entity.Course;

public class CourseGenerationUtil {

    public static final String TITLE = "test_Title";
    public static final String TEACHER = "test_Teacher";

    public static Course generateCourse() {
        Course course = new Course();
        course.setTitle(TITLE);
        course.setTeacher(TEACHER);
        return course;
    }

    public static Course generateCourse(String title, String teacher) {
        Course course = new Course();
        course.setTitle(title);
        course.setTeacher(teacher);
        return course;
    }

    public static Course generateCourse(String title) {
        Course course = new Course();
        course.setTitle(title);
        course.setTeacher(TEACHER);
        return course;
    }
}
