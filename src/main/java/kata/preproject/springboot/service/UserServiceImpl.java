package kata.preproject.springboot.service;

import kata.preproject.springboot.model.User;
import kata.preproject.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User getUserById(Long id) {
        return userRepository.getById(id);
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

//    @Transactional
//    @Override
//    public void updateUser(Long id, User updateUser) {
//        if (!updateUser.getPassword().contains("$2a$12$")) {
//            updateUser.setPassword(bCryptPasswordEncoder.encode(updateUser.getPassword()));
//        }
//        userRepository.updateUser(id, updateUser);
//    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
