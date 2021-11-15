package ua.com.alevel.dao;

import ua.com.alevel.db.GeneralDB;
import ua.com.alevel.entity.Course;
import ua.com.alevel.util.CustomArray;

public class CourseDAO {

    public void create(Course course) {
        GeneralDB.getInstance().create(course);
    }

    public void update(Course course) {
        GeneralDB.getInstance().update(course);
    }

    public void delete(String id) {
        GeneralDB.getInstance().deleteCourse(id);
    }

    public Course findById(String id) {
        return GeneralDB.getInstance().findCourseViaId(id);
    }

    public CustomArray<Course> findAll() {
        return GeneralDB.getInstance().findAllCourses();
    }


}
