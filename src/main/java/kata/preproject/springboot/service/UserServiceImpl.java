package kata.preproject.springboot.service;

import kata.preproject.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import kata.preproject.springboot.exception.UserNotFoundException;
import kata.preproject.springboot.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptpasswordencoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bcryptpasswordencoder) {
        this.userRepository = userRepository;
        this.bcryptpasswordencoder = bcryptpasswordencoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "firstname", "lastname"));
    }

    @Override
    public User getOneById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User saveUser(User user, BindingResult bindingResult) {
        user.setPassword(bcryptpasswordencoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(User user, BindingResult bindingResult) {
        user.setPassword(user.getPassword().isEmpty() ?
                getOneById(user.getId()).getPassword() :
                bcryptpasswordencoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
