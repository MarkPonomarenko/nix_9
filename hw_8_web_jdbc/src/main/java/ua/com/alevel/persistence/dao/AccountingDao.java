package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.entity.Accounting;

import java.util.List;

public interface AccountingDao extends BaseDao<Accounting> {

    List<Accounting> findCourseStudents(Long courseId);

    List<Accounting> findStudentCourses(Long studentId);

    Accounting findByBothId(Long courseId, Long studentId);
}
