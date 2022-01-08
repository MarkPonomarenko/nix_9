package ua.com.alevel.view.dto.response;

import org.apache.commons.collections4.CollectionUtils;
import ua.com.alevel.persistence.entity.Student;

import java.util.Date;

public class StudentResponseDto extends ResponseDto {


    private String lastName;
    private String firstName;
    private Integer age;
    private Date created;
    private Date updated;
    private long courseCount;

    public StudentResponseDto() {
    }

    public StudentResponseDto(Student student) {
        setId(student.getId());
        this.lastName = student.getLastName();
        this.firstName = student.getFirstName();
        this.age = student.getAge();
        this.created = student.getCreated();
        this.updated = student.getUpdated();
        if (CollectionUtils.isNotEmpty(student.getCourses())) {
            this.courseCount = student.getCourses().size();
        }
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Integer getAge() {
        return age;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setCourseCount(long courseCount) {
        this.courseCount = courseCount;
    }

    public long getCourseCount() {
        return courseCount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "StudentResponseDto{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", age=" + age +
                '}';
    }
}
