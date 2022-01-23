package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.dao.AccountDao;
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
import java.util.Set;

@Service
@Transactional
public class AccountDaoImpl implements AccountDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Operation> getOperations(Long accountId) {
        return entityManager.find(Account.class, accountId).getOperations();
    }

    @Override
    public void addOperation(Long accountId, Long incomeId) {
        Account account = entityManager.find(Account.class, accountId);
        Operation operation = entityManager.find(Operation.class, incomeId);
        account.addOperation(operation);
    }

    @Override
    public void removeOperation(Long accountId, Long incomeId) {
        Account account = entityManager.find(Account.class, accountId);
        Operation operation = entityManager.find(Operation.class, incomeId);
        account.removeOperation(operation);
    }

    @Override
    public void create(Account entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(Account entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Long id) {
        int isSuccessful = entityManager.createQuery("delete from Account d where d.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        if (isSuccessful == 0) {
            throw new OptimisticLockException("accounts modified concurrently");
        }
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager.createQuery("select count(d.id) from Account d where d.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Account findById(Long id) {
        return entityManager.find(Account.class, id);
    }

    @Override
    public DataTableResponse<Account> findAll(DataTableRequest request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        if (request.getOrder().equals("desc")) {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(request.getSort())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(request.getSort())));
        }

        int page = (request.getCurrentPage() - 1) * request.getPageSize();
        int size = page + request.getPageSize();

        System.out.println(entityManager.createQuery(criteriaQuery).unwrap(org.hibernate.Query.class).getQueryString());

        List<Account> items = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();

        DataTableResponse<Account> response = new DataTableResponse<>();
        response.setSort(request.getSort());
        response.setOrder(request.getOrder());
        response.setCurrentPage(request.getCurrentPage());
        response.setPageSize(request.getPageSize());
        response.setItems(items);

        return response;
    }

    @Override
    public long count() {
        Query query = entityManager.createQuery("select count(d.id) from Account d");
        return (Long) query.getSingleResult();
    }

    @Override
    public Account getLastRecord() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        criteriaQuery.orderBy(criteriaBuilder.desc(from.get("id")));
        List<Account> items = entityManager.createQuery(criteriaQuery)
                .setMaxResults(1)
                .getResultList();
        return (items.get(0));
    }
}
