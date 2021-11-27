package web.dao;

import web.model.Role;

import java.util.List;
import java.util.NoSuchElementException;

public interface RoleDao {
//    List<Role> findAllRoles();
//    Role findRoleByAuthority(String authority) throws NoSuchElementException;
    Role getRole(Long id);
}
