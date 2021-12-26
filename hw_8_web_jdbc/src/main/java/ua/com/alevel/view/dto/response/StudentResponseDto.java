package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.Student;

public class StudentResponseDto extends ResponseDto{

    private String lastName;
    private String firstName;
    private Integer age;

    public StudentResponseDto() {}

    public StudentResponseDto(Student student) {
        setId(student.getId());
        setCreated(student.getCreated());
        setUpdated(student.getUpdated());
        this.lastName = student.getLastName();
        this.firstName = student.getFirstName();
        this.age = student.getAge();
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

    @Override
    public String toString() {
        return "StudentResponseDto{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", age=" + age +
                '}';
    }
}
