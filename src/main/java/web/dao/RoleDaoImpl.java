package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> findAllRoles() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    public Role findRoleByName(String roleName) {
        return (Role) entityManager.createQuery("select r from Role r where r.name=:role")
                .setParameter("role", roleName)
                .getSingleResult();
    }

    @Override
    public HashSet<Role> getSetOfRoles(String[] rolesNames) {
        var roleSet = new HashSet<Role>();
        for (String role : rolesNames) {
            roleSet.add(findRoleByName(role));
        }
        return roleSet;
    }
}
