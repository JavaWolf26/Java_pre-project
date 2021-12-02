package kata.preproject.springboot.controller;

import kata.preproject.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserDetailsService userDetailsService;

    @Autowired
    public UserController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping(value = "")
    public String printUser(@CurrentSecurityContext(expression = "authentication.principal") User principal,
                            Model model) {
        model.addAttribute("user", principal);
        return "user";
    }

    @GetMapping(value = "/{email}")
    public String printUserByEmail(@CurrentSecurityContext(expression = "authentication.principal") User principal,
                                   @PathVariable("email") String email, Model model) {
        model.addAttribute("user", principal);
        model.addAttribute("user", userDetailsService.loadUserByUsername(email));
        return "user";
    }
}
