package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.entity.Course;

import java.util.List;

public interface CourseDao extends BaseDao<Course> {

    List<Course> findAll();
}
