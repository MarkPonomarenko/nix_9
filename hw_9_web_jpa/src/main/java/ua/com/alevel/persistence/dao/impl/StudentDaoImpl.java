package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.dao.StudentDao;
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
public class StudentDaoImpl implements StudentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Student student) {
        entityManager.persist(student);
    }

    @Override
    public void update(Student student) {
        entityManager.merge(student);
    }

    @Override
    public void delete(Long id) {
        int isSuccessful = entityManager.createQuery("delete from Student d where d.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        if (isSuccessful == 0) {
            throw new OptimisticLockException("courses modified concurrently");
        }
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager.createQuery("select count(d.id) from Student d where d.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Student findById(Long id) {
        return entityManager.find(Student.class, id);
    }


    @Override
    public DataTableResponse<Student> findAll(DataTableRequest request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> from = criteriaQuery.from(Student.class);
        if (request.getOrder().equals("desc")) {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(request.getSort())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(request.getSort())));
        }

        int page = (request.getPage() - 1) * request.getSize();
        int size = page + request.getSize();

        List<Student> items = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();

        DataTableResponse<Student> response = new DataTableResponse<>();
        response.setSort(request.getSort());
        response.setOrder(request.getOrder());
        response.setCurrentPage(request.getPage());
        response.setPageSize(request.getSize());
        response.setItems(items);

        return response;
    }

    @Override
    public long count() {
        Query query = entityManager.createQuery("select count(d.id) from Student d");
        return (Long) query.getSingleResult();
    }

    @Override
    public Set<Course> findCourseStudents(Long id) {
        return entityManager.find(Student.class, id).getCourses();
    }

    @Override
    public void assignCourse(Long courseId, Long studentId) {
        Course course = entityManager.find(Course.class, courseId);
        Student student = entityManager.find(Student.class, studentId);
        student.addCourse(course);
    }

    @Override
    public void unassignCourse(Long courseId, Long studentId) {
        Course course = entityManager.find(Course.class, courseId);
        Student student = entityManager.find(Student.class, studentId);
        student.removeCourse(course);
    }

    @Override
    public List<Student> findAll() {
        return entityManager.createQuery("select x from Student x", Student.class).getResultList();
    }
}
