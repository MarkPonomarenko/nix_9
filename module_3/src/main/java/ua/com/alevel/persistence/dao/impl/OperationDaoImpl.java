package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.dao.OperationDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.Operation;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Service
@Transactional
public class OperationDaoImpl implements OperationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Operation entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(Operation entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Long id) {
        int isSuccessful = entityManager.createQuery("delete from Operation d where d.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        if (isSuccessful == 0) {
            throw new OptimisticLockException("incomes modified concurrently");
        }
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager.createQuery("select count(d.id) from Operation d where d.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Operation findById(Long id) {
        return entityManager.find(Operation.class, id);
    }

    @Override
    public DataTableResponse<Operation> findAll(DataTableRequest request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Operation> criteriaQuery = criteriaBuilder.createQuery(Operation.class);
        Root<Operation> from = criteriaQuery.from(Operation.class);
        if (request.getOrder().equals("desc")) {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(request.getSort())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(request.getSort())));
        }

        int page = (request.getCurrentPage() - 1) * request.getPageSize();
        int size = page + request.getPageSize();

        List<Operation> items = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();

        DataTableResponse<Operation> response = new DataTableResponse<>();
        response.setSort(request.getSort());
        response.setOrder(request.getOrder());
        response.setCurrentPage(request.getCurrentPage());
        response.setPageSize(request.getPageSize());
        response.setItems(items);

        return response;
    }

    @Override
    public long count() {
        Query query = entityManager.createQuery("select count(d.id) from Operation d");
        return (Long) query.getSingleResult();
    }

    @Override
    public Operation getLastRecord() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Operation> criteriaQuery = criteriaBuilder.createQuery(Operation.class);
        Root<Operation> from = criteriaQuery.from(Operation.class);
        criteriaQuery.orderBy(criteriaBuilder.desc(from.get("id")));
        List<Operation> items = entityManager.createQuery(criteriaQuery)
                .setMaxResults(1)
                .getResultList();
        return (items.get(0));
    }
}
