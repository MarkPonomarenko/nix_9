package ua.com.alevel.db;

import ua.com.alevel.entity.Course;
import ua.com.alevel.entity.Student;
import ua.com.alevel.util.CustomArray;

import java.util.UUID;

public class GeneralDB {

    private static GeneralDB instance;
    private CustomArray<Student> students = new CustomArray<>();
    private CustomArray<Course> courses = new CustomArray<>();

    private GeneralDB() {

    }

    public static GeneralDB getInstance() {
        if (instance == null) {
            instance = new GeneralDB();
        }
        return instance;
    }

    public void create(Student student) {
        student.setId(generateId(1));
        students.add(student);
    }

    public void create(Course course) {
        course.setId(generateId(2));
        courses.add(course);
    }

    public void update(Student student) {
        Student updStudent = findStudentViaId(student.getId());
        updStudent.setFirstName(student.getFirstName());
        updStudent.setLastName(student.getLastName());
        updStudent.setCourses(student.getCourses());
    }

    public void update(Course course) {
        Course updCourse = findCourseViaId(course.getId());
        updCourse.setTeacher(course.getTeacher());
        updCourse.setTitle(course.getTitle());
    }

    public Student findStudentViaId(String id) {
        for (int i = 0; i < students.size(); ++i) {
            if (students.getAt(i).getId().equals(id)) {
                return students.getAt(i);
            }
        }
        return null;
    }

    public Course findCourseViaId(String id) {
        for (int i = 0; i < courses.size(); ++i) {
            if (courses.getAt(i).getId().equals(id)) {
                return courses.getAt(i);
            }
        }
        return null;
    }

    public void deleteStudent(String id) {
        for (int i = 0; i < students.size(); ++i) {
            if (students.getAt(i).getId().equals(id)) {
                students.removeAt(i);
            }
        }
    }

    public void deleteCourse(String id) {
        for (int i = 0; i < courses.size(); ++i) {
            if (courses.getAt(i).getId().equals(id)) {
                courses.removeAt(i);
            }
        }
    }

    public CustomArray<Student> findAllStudents() {
        return students;
    }

    public CustomArray<Course> findAllCourses() {
        return courses;
    }

    public String generateId(int entity) {
        String id = UUID.randomUUID().toString();
        switch (entity) {
            case 1:
                for (int i = 0; i < students.size(); ++i) {
                    if (students.getAt(i).getId().equals(id)) {
                        return generateId(entity);
                    }
                }
                break;
            case 2:
                for (int i = 0; i < courses.size(); ++i) {
                    if (courses.getAt(i).getId().equals(id)) {
                        return generateId(entity);
                    }
                }

        }
        return id;
    }
}
