package web.dao;

import web.model.Role;

import java.util.HashSet;
import java.util.List;

public interface RoleDao {
    List<Role> findAllRoles();
    Role findRoleByName(String name);
    HashSet<Role> getSetOfRoles(String[] roleSet);
}
