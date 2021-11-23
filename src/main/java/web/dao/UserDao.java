package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao /*extends JpaRepository<User, Long>*/ {

//    User findByUsername(String username);

    List<User> getAllUsers();
    User getUserById(Long id);
    void saveUser(User user);
    void updateUser(Long id, User updateUser);
    void deleteUser(Long id);
}
