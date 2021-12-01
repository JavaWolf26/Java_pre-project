package kata.preproject.springboot.service;

import kata.preproject.springboot.model.Role;
import kata.preproject.springboot.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    @Override
    public Set<Role> getSetOfRoles(String[] roleSet) {
        Set<Role> rolesSet = new HashSet<>();
        for (String role : roleSet){
            rolesSet.add(roleRepository.findRoleByName(role));
        }
        return rolesSet;
    }
}
