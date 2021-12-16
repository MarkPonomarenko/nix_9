package ua.com.alevel.db;

import ua.com.alevel.parser.CSVParser;
import ua.com.alevel.parser.EntityParser;
import ua.com.alevel.entity.Course;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class CoursesInMemory {

    private List<Course> courses;
    String fileStorage = "courses.csv";
    private static CoursesInMemory instance;

    public CoursesInMemory() {
        courses = new ArrayList<>();
    }

    private static CoursesInMemory getInstance() {
        if (instance == null) {
            instance = new CoursesInMemory();
        }
        return instance;
    }

    public List<Course> findAll() {
        List<List<String>> file = CSVParser.read(fileStorage);
        courses = EntityParser.toEntities(file, Course.class);
        return courses;
    }

    public void delete(String id) {
        List<List<String>> file = CSVParser.read(fileStorage);
        List<Course> courseList = EntityParser.toEntities(file, Course.class);
        deleteAll();
        Iterator<Course> iterator = courseList.iterator();
        while (iterator.hasNext()) {
            Course tmp = iterator.next();
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

    public Course findById(String id) {
        List<Course> courses = findAll();
        for (Course course : courses) {
            if (Objects.equals(course.getId(), id)) {
                return course;
            }
        }
        return null;
    }

    public void update(Course course) {
        List<List<String>> file = CSVParser.read(fileStorage);
        List<Course> courseList = EntityParser.toEntities(file, Course.class);
        deleteAll();
        for (Course it : courseList) {
            if (it.getId().equals(course.getId())) {
                it.setCredits(course.getCredits());
                it.setTitle(course.getTitle());
                it.setTeacher(course.getTeacher());
                it.setStudents(course.getStudents());

            }
            create(it);
        }
    }

    public void create(Course course) {
        try {
            List<String> string = EntityParser.entToString(course);
            if (CSVParser.isEmpty(fileStorage)) {
                CSVParser.write(List.of(EntityParser.getFields(Course.class), string), fileStorage);
            } else {
                CSVParser.write(List.of(string), fileStorage);
            }
        } catch (IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    }

}
