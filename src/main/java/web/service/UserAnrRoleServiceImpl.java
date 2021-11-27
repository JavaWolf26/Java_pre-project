package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDao;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserAnrRoleServiceImpl implements UserAnrRoleService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserAnrRoleServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getRole(2L));
//        for (Role role : user.getRoles()) {
//            role.setId(roleDao.findRoleByAuthority(role.getAuthority()).getId());
//        }
//        for (Role role : roleDao.findAllRoles()) {
//            roles.add(roleDao.getRole(roleDao.findRoleByAuthority(role.getAuthority()).getId()));
////            role.setId(roleDao.findRoleByAuthority(role.getAuthority()).getId());
//        }
        user.setRoles(roles);
        userDao.saveUser(user);
    }

    @Transactional
    @Override
    public void updateUser(Long id, User updateUser) {
        userDao.updateUser(id, updateUser);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleDao.findAllRoles();
    }
}
