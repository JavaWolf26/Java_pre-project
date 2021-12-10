package kata.preproject.springboot.service;

import kata.preproject.springboot.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void saveUser(User user);
    void updateUser(Long id, User updateUser);
    void deleteUser(Long id);
}
