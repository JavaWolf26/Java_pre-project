package web.dao;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

//    @Override
//    public List<Role> findAllRoles() {
//        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
//    }

//    @Override
//    public Role findRoleByAuthority(String authority) throws NoSuchElementException {
//        return findAllRoles().stream()
//                .filter(r -> authority.equals(r.getAuthority()))
//                .findFirst()
//                .orElseThrow(() -> new NoSuchElementException(String.format("Role %s not found", authority)));
//    }

    @Override
    public Role getRole(Long id) {
        TypedQuery<Role> tq = entityManager.createQuery("select r from Role r where r.id = :id", Role.class);
        tq.setParameter("id", id);
        Role role = tq.getResultList().stream().findAny().orElse(null);
        if (role == null) {
            throw new ResourceNotFoundException("Role with the specified id " + id + " does not exist.");
        } else return role;
    }
}
