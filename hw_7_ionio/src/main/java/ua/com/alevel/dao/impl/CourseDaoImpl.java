package ua.com.alevel.dao.impl;

import ua.com.alevel.db.CoursesInMemory;
import ua.com.alevel.dao.CourseDao;
import ua.com.alevel.entity.Course;

import java.util.List;
import java.util.UUID;


public class CourseDaoImpl implements CourseDao {

    private final CoursesInMemory courseDB = new CoursesInMemory();

    @Override
    public void create(Course course) {
        course.setId(generateId());
        courseDB.create(course);
    }

    @Override
    public void update(Course course) {
        courseDB.update(course);
    }

    @Override
    public void delete(String id) {
        courseDB.delete(id);
    }

    @Override
    public void deleteAll() {
        courseDB.deleteAll();
    }

    @Override
    public Course findById(String id) {
        return courseDB.findById(id);
    }

    @Override
    public List<Course> findAll() {
        return courseDB.findAll();
    }

    public String generateId() {
        String id = UUID.randomUUID().toString();
        return id;
    }
}
