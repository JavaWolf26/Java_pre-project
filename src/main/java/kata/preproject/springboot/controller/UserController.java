package kata.preproject.springboot.controller;

import kata.preproject.springboot.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public UserController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping({""})
    public String main(Model model, @Nullable Authentication auth) {
        return userDetailsService.getPage(model, auth);
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }
}
