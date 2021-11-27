package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String email);
    List<User> getAllUsers();
    User getUserById(Long id);
    void saveUser(User user);
    void updateUser(Long id, User updateUser);
    void deleteUser(Long id);

    List<Role> findAllRoles();
}
