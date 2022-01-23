package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.dao.UserDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.User;

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
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(User entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(User entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Long id) {
        int isSuccessful = entityManager.createQuery("delete from User d where d.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        if (isSuccessful == 0) {
            throw new OptimisticLockException("users modified concurrently");
        }
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager.createQuery("select count(d.id) from User d where d.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public DataTableResponse<User> findAll(DataTableRequest request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> from = criteriaQuery.from(User.class);
        if (request.getOrder().equals("desc")) {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(request.getSort())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(request.getSort())));
        }

        int page = (request.getCurrentPage() - 1) * request.getPageSize();
        int size = page + request.getPageSize();

        List<User> items = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();

        DataTableResponse<User> response = new DataTableResponse<>();
        response.setSort(request.getSort());
        response.setOrder(request.getOrder());
        response.setCurrentPage(request.getCurrentPage());
        response.setPageSize(request.getPageSize());
        response.setItems(items);

        return response;
    }

    @Override
    public long count() {
        Query query = entityManager.createQuery("select count(d.id) from User d");
        return (Long) query.getSingleResult();
    }

    @Override
    public Set<Account> getAccounts(Long userId) {
        return entityManager.find(User.class, userId).getAccounts();
    }

    @Override
    public void addAccount(Long userId, Long accountId) {
        User user = entityManager.find(User.class, userId);
        Account account = entityManager.find(Account.class, accountId);
        user.addAccount(account);
    }

    @Override
    public void removeAccount(Long userId, Long accountId) {
        User user = entityManager.find(User.class, userId);
        Account account = entityManager.find(Account.class, accountId);
        user.removeAccount(account);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select x from User x", User.class).getResultList();
    }
}
