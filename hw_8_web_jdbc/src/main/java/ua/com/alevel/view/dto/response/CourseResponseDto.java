package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.Course;

public class CourseResponseDto extends ResponseDto{

    private String title;
    private String teacher;
    private Integer credits;
    private Integer studentCount;

    public CourseResponseDto() {}

    public CourseResponseDto(Course course) {
        setId(course.getId());
        setCreated(course.getCreated());
        setUpdated(course.getUpdated());
        this.title = course.getTitle();
        this.teacher = course.getTeacher();
        this.credits = course.getCredits();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    @Override
    public String toString() {
        return "CourseResponseDto{" +
                "title='" + title + '\'' +
                ", teacher='" + teacher + '\'' +
                ", credits=" + credits +
                ", studentCount=" + studentCount +
                '}';
    }
}
