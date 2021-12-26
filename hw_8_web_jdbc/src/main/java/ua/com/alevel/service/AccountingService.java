package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.Accounting;

import java.util.List;

public interface AccountingService extends BaseService<Accounting>{

    List<Accounting> findCourseStudents(Long courseId);

    List<Accounting> findStudentCourses(Long studentId);

    Accounting findByBothId(Long courseId, Long studentId);
}
