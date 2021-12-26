package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.Accounting;

public class AccountingResponseDto extends ResponseDto{

    private Long courseId;
    private Long studentId;

    public AccountingResponseDto(Accounting accounting) {
        if (accounting != null) {
            super.setId(accounting.getId());
            this.courseId = accounting.getCourseId();
            this.studentId = accounting.getStudentId();
        }
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "AccountingResponseDto{" +
                "courseId=" + courseId +
                ", studentId=" + studentId +
                '}';
    }
}
