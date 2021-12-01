package kata.preproject.springboot.service;

import kata.preproject.springboot.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> findAllRoles();
    Set<Role> getSetOfRoles(String[] roleSet);
}
