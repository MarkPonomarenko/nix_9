package ua.com.alevel.persistence.entity;

public class Accounting extends BaseEntity {

    private Long studentId;
    private Long courseId;

    public Accounting() {
        super();
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Accounting{" +
                "id=" + super.getId() +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                '}';
    }
}
