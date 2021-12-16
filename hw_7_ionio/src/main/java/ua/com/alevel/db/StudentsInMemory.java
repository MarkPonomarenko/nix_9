package ua.com.alevel.db;

import ua.com.alevel.entity.Student;
import ua.com.alevel.parser.CSVParser;
import ua.com.alevel.parser.EntityParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class StudentsInMemory {

    private List<Student> students;
    String fileStorage = "students.csv";
    private static StudentsInMemory instance;

    public StudentsInMemory() {
        students = new ArrayList<>();
    }

    private static StudentsInMemory getInstance() {
        if (instance == null) {
            instance = new StudentsInMemory();
        }
        return instance;
    }

    public List<Student> findAll() {
        List<List<String>> file = CSVParser.read(fileStorage);
        students = EntityParser.toEntities(file, Student.class);
        return students;
    }

    public void delete(String id) {
        List<List<String>> file = CSVParser.read(fileStorage);
        List<Student> studentList = EntityParser.toEntities(file, Student.class);
        deleteAll();
        Iterator<Student> iterator = studentList.iterator();
        while (iterator.hasNext()) {
            Student tmp = iterator.next();
            if (Objects.equals(tmp.getId(), id)) {
                iterator.remove();
            } else {
                create(tmp);
            }
        }
    }

    public void deleteAll() {
        try {
            PrintWriter deleter = new PrintWriter(CSVParser.getPath(fileStorage));
            deleter.write("");
            deleter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Student findById(String id) {
        List<Student> students = findAll();
        for (Student student : students) {
            if (Objects.equals(student.getId(), id)) {
                return student;
            }
        }
        return null;
    }

    public void update(Student student) {
        List<List<String>> file = CSVParser.read(fileStorage);
        List<Student> studentList = EntityParser.toEntities(file, Student.class);
        deleteAll();
        for (Student it : studentList) {
            if (it.getId().equals(student.getId())) {
                it.setAge(student.getAge());
                it.setLastName(student.getLastName());
                it.setFirstName(student.getFirstName());
                it.setCourses(student.getCourses());
            }
            create(it);
        }
    }

    public void create(Student student) {
        try {
            List<String> string = EntityParser.entToString(student);
            if (CSVParser.isEmpty(fileStorage)) {
                CSVParser.write(List.of(EntityParser.getFields(Student.class), string), fileStorage);
            } else {
                CSVParser.write(List.of(string), fileStorage);
            }
        } catch (IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    }


}
