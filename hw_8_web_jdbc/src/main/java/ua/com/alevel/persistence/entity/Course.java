package ua.com.alevel.persistence.entity;

public class Course extends BaseEntity {

    private String title;
    private String teacher;
    private Integer credits;

    public Course() {
        super();
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

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", teacher='" + teacher + '\'' +
                ", credits=" + credits +
                '}';
    }
}
