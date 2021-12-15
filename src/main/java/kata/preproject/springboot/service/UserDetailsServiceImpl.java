package kata.preproject.springboot.service;

import kata.preproject.springboot.model.User;
import kata.preproject.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", email)));
    }

    public String getPage(Model model, @Nullable Authentication auth) {
        if (Objects.isNull(auth)) {
            return "hello";
        }
        User user = (User) auth.getPrincipal();
        model.addAttribute("user", user);
        if (user.hasRole("ROLE_ADMIN")) {
            return "admin";
        } else
            return "user";
    }
}
