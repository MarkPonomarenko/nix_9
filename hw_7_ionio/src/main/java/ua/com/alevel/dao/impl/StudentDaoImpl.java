package ua.com.alevel.dao.impl;

import ua.com.alevel.db.StudentsInMemory;
import ua.com.alevel.dao.StudentDao;
import ua.com.alevel.entity.Student;


import java.util.List;
import java.util.UUID;

public class StudentDaoImpl implements StudentDao {

    private final StudentsInMemory studentDB = new StudentsInMemory();

    @Override
    public void create(Student student) {
        student.setId(generateId());
        studentDB.create(student);
    }

    @Override
    public void update(Student student) {
        studentDB.update(student);
    }

    @Override
    public void delete(String id) {
        studentDB.delete(id);
    }

    @Override
    public void deleteAll() {
        studentDB.deleteAll();
    }

    @Override
    public Student findById(String id) {
        return studentDB.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return studentDB.findAll();
    }

    public String generateId() {
        String id = UUID.randomUUID().toString();
        return id;
    }
}
