package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String email);
    List<User> getAllUsers();
    User getUserById(Long id);
    void saveUser(User user, Model model);
    void updateUser(Long id, User updateUser);
    void deleteUser(Long id);

    List<Role> findAllRoles();
}
