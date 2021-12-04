//package kata.preproject.springboot.controller;
//
//import kata.preproject.springboot.model.Role;
//import kata.preproject.springboot.model.User;
//import kata.preproject.springboot.repository.RoleRepository;
//import kata.preproject.springboot.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.HashSet;
//import java.util.Set;
//
//@Component
//public class AdminData {
//
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//
//    @Autowired
//    public AdminData(UserRepository userRepository, RoleRepository roleRepository) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//    }
//
//    @PostConstruct
//    public void insertData() {
//        roleRepository.save(new Role("ROLE_ADMIN"));
//        roleRepository.save(new Role("ROLE_USER"));
//
//        Set<Role> roles = new HashSet<>();
//        roles.add(roleRepository.findRoleByName("ROLE_ADMIN"));
//        roles.add(roleRepository.findRoleByName("ROLE_USER"));
//        User admin = new User();
//        admin.setFirstname("Admin");
//        admin.setPassword("$2a$12$ewvWbdBj/34U5MC7wHFKZOeBMyc9xRPBMkdhqq/YzA7gT0z21J6oa");
//        admin.setLastname("Adminov");
//        admin.setEnabled(true);
//        admin.setAge((byte) 10);
//        admin.setEmail("admin@email.ru");
//        admin.setRoles(roles);
//        userRepository.save(admin);
//
//        roles.clear();
//        roles.add(roleRepository.findRoleByName("ROLE_USER"));
//        User user = new User();
//        user.setFirstname("User");
//        user.setPassword("$2a$12$xr1XtdcnDisJRd1XwJNUTOWXPijwJiVidRS750YfQUuay9zjCEEJy");
//        user.setLastname("Userov");
//        user.setEnabled(true);
//        user.setAge((byte) 11);
//        user.setEmail("user@email.ru");
//        user.setRoles(roles);
//        userRepository.save(user);
//    }
//}
