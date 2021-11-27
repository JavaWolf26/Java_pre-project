package web.service;

import web.model.Role;

import java.util.HashSet;
import java.util.List;

public interface RoleService {
    List<Role> findAllRoles();
    HashSet<Role> getSetOfRoles(String[] roleSet);
}
