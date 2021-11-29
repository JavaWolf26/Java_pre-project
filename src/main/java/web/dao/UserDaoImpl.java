package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select distinct u from User u left join fetch u.roles",
                User.class).getResultList();
    }

    @Override
    public User getUserByName(String email) {
        TypedQuery<User> tq = entityManager.createQuery("select distinct u from User u " +
                "left join fetch u.roles where u.email = :email", User.class);
        tq.setParameter("email", email);
        return tq.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public User getUserById(Long id) {
        TypedQuery<User> tq = entityManager.createQuery("select distinct u from User u " +
                "left join fetch u.roles where u.id = :id", User.class);
        tq.setParameter("id", id);
        return tq.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(Long id, User updateUser) {
        entityManager.merge(updateUser);
    }

    @Override
    public void deleteUser(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }
}
