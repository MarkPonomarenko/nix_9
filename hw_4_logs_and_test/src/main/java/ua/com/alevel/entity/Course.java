package ua.com.alevel.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.alevel.util.CustomArray;


@Getter
@ToString
public class Course {

    private String id;
    private String title;
    private String teacher;
    private CustomArray<Student> students;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
        if (title == null)
            this.title = "Undefined";
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
        if (teacher == null)
            this.teacher = "Unknown";
    }

    public void setStudents(CustomArray<Student> students) {
        this.students = students;
    }
}
