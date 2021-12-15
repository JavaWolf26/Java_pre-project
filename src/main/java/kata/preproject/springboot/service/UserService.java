package kata.preproject.springboot.service;

import kata.preproject.springboot.model.User;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getOneById(Long id);
    User saveUser(User user, BindingResult bindingResult);
    User updateUser(User user, BindingResult bindingResult);
    void deleteUser(Long id);
}
