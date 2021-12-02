package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDao;
import web.model.Role;

import java.util.HashSet;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    @Override
    public List<Role> findAllRoles() {
        return roleDao.findAllRoles();
    }

    @Transactional
    @Override
    public HashSet<Role> getSetOfRoles(String[] roleSet) {
        if (roleSet.length != 0) {
            return roleDao.getSetOfRoles(roleSet);
        } else {
            String[] nameRole = {"ROLE_USER"};
            return roleDao.getSetOfRoles(nameRole);
        }
    }
}
