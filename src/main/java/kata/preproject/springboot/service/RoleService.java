package kata.preproject.springboot.service;

import kata.preproject.springboot.model.Role;

public interface RoleService {
    Iterable<Role> findAllRoles();
}
