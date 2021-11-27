package web.service;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserAnrRoleService {
    List<User> getAllUsers();
    User getUserById(Long id);
    void saveUser(User user);
    void updateUser(Long id, User updateUser);
    void deleteUser(Long id);

    List<Role> findAllRoles();
}
