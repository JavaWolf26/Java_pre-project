package web.dao;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUserById(Long id) {
        TypedQuery<User> tq = entityManager.createQuery("select u from User u where u.id = :id", User.class);
        tq.setParameter("id", id);
        User user = tq.getResultList().stream().findAny().orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User with the specified id " + id + " does not exist.");
        } else return user;
    }

    @Override
    public void saveUser(User user) {
        Query nativeQuery = entityManager
                .createNativeQuery("insert into users (firstName, lastName, age, email) values (?, ?, ?, ?)");
        nativeQuery.setParameter(1, user.getFirstName());
        nativeQuery.setParameter(2, user.getLastName());
        nativeQuery.setParameter(3, user.getAge());
        nativeQuery.setParameter(4, user.getEmail());
        nativeQuery.executeUpdate();
    }

    @Override
    public void updateUser(Long id, User updateUser) {
        Query nativeQuery = entityManager
                .createNativeQuery("update users set firstName = ?, lastName=?, age=?, email=? where id = ?");
        nativeQuery.setParameter(1, updateUser.getFirstName());
        nativeQuery.setParameter(2, updateUser.getLastName());
        nativeQuery.setParameter(3, updateUser.getAge());
        nativeQuery.setParameter(4, updateUser.getEmail());
        nativeQuery.setParameter(5, id);
        nativeQuery.executeUpdate();
    }

    @Override
    public void deleteUser(Long id) {
        Query nativeQuery = entityManager.createNativeQuery("delete from users where id=?");
        nativeQuery.setParameter(1, id);
        nativeQuery.executeUpdate();
    }
}
