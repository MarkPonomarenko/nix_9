package ua.com.alevel.dao;

import ua.com.alevel.entity.Student;
import ua.com.alevel.db.GeneralDB;
import ua.com.alevel.util.CustomArray;

public class StudentDAO {

    public void create(Student student) {
        GeneralDB.getInstance().create(student);
    }

    public void update(Student student) {
        GeneralDB.getInstance().update(student);
    }

    public void delete(String id) {
        GeneralDB.getInstance().deleteStudent(id);
    }

    public Student findById(String id) {
        return GeneralDB.getInstance().findStudentViaId(id);
    }

    public CustomArray<Student> findAll() {
        return GeneralDB.getInstance().findAllStudents();
    }
}
