package ua.com.alevel.view.dto.response;

import org.apache.commons.collections4.CollectionUtils;
import ua.com.alevel.persistence.entity.Course;

import java.util.Date;

public class CourseResponseDto extends ResponseDto {

    private String title;
    private String teacher;
    private Integer credits;

    private Integer studentCount;

    public CourseResponseDto() {
    }

    public CourseResponseDto(Course course) {
        setId(course.getId());
        setUpdated(course.getUpdated());
        setCreated(course.getCreated());
        this.title = course.getTitle();
        this.teacher = course.getTeacher();
        this.credits = course.getCredits();
        if (CollectionUtils.isNotEmpty(course.getStudents())) {
            this.studentCount = course.getStudents().size();
            return;
        }
        this.studentCount = 0;
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
