package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.dao.CourseDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Course;
import ua.com.alevel.persistence.entity.Student;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;


@Repository
@Transactional
public class CourseDaoImpl implements CourseDao {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Course course) {
        entityManager.persist(course);
    }

    @Override
    public void update(Course course) {
        entityManager.merge(course);
    }

    @Override
    public void delete(Long id) {
        int isSuccessful = entityManager.createQuery("delete from Course d where d.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        if (isSuccessful == 0) {
            throw new OptimisticLockException("students modified concurrently");
        }
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager.createQuery("select count(d.id) from Course d where d.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Course findById(Long id) {
        return entityManager.find(Course.class, id);
    }

    @Override
    public DataTableResponse<Course> findAll(DataTableRequest request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> from = criteriaQuery.from(Course.class);
        if (request.getOrder().equals("desc")) {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(request.getSort())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(request.getSort())));
        }

        int page = (request.getPage() - 1) * request.getSize();
        int size = page + request.getSize();

        List<Course> items = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
        System.out.println(items);
        DataTableResponse<Course> response = new DataTableResponse<>();
        response.setSort(request.getSort());
        response.setOrder(request.getOrder());
        response.setCurrentPage(request.getPage());
        response.setPageSize(request.getSize());
        response.setItems(items);

        return response;
    }

    @Override
    public long count() {
        Query query = entityManager.createQuery("select count(d.id) from Course d");
        return (Long) query.getSingleResult();
    }

    @Override
    public Set<Student> findCourseStudents(Long id) {
        return entityManager.find(Course.class, id).getStudents();
    }

    @Override
    public void assignStudent(Long courseId, Long studentId) {
        Course course = entityManager.find(Course.class, courseId);
        Student student = entityManager.find(Student.class, studentId);
        course.addStudent(student);
    }

    @Override
    public void unassignStudent(Long courseId, Long studentId) {
        Course course = entityManager.find(Course.class, courseId);
        Student student = entityManager.find(Student.class, studentId);
        course.removeStudent(student);
    }

    @Override
    public List<Course> findAll() {
        return entityManager.createQuery("select x from Course x", Course.class).getResultList();
    }
}
