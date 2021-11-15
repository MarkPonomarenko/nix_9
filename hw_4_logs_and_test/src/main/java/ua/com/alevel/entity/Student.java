package ua.com.alevel.entity;

import lombok.Getter;
import ua.com.alevel.util.CustomArray;

@Getter
public class Student {

    private int age;
    private String id;
    private String firstName;
    private String lastName;
    private CustomArray<Course> courses;

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        if (firstName == null)
            this.firstName = "Unknown";
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        if (lastName == null)
            this.lastName = "Unknown";
    }

    public void setCourses(CustomArray<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        StringBuilder coursesOut = new StringBuilder();
        if (courses != null)
            for (int i = 0; i < courses.size(); ++i) {
                if (i != courses.size() - 1) {
                    if (courses.getAt(i) != null)
                        coursesOut.append(courses.getAt(i).getTitle()).append(", ");
                } else if (courses.getAt(i) != null)
                    coursesOut.append(courses.getAt(i).getTitle());
            }
        return "Студент ['" + id + "', "
                + lastName + " " + firstName
                + ", Возраст: " + age + ", Курсы: " + coursesOut + "]";
    }
}
