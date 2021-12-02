package kata.preproject.springboot.controller;

import kata.preproject.springboot.model.User;
import kata.preproject.springboot.service.RoleService;
import kata.preproject.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.roleService = roleService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/users")
    public String printAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/users/{id}")
    public String printUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "index";
    }

    @GetMapping(value = "/user/{email}")
    public String printUserByEmail(@CurrentSecurityContext(expression = "authentication.principal") User principal,
                            @PathVariable("email") String email, Model model) {
        model.addAttribute("user", principal);
        model.addAttribute("user", userDetailsService.loadUserByUsername(email));
        return "user";
    }

    @GetMapping("/users/new")
    public String createUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allRoles", roleService.findAllRoles());
        return "new";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                           @RequestParam(value = "nameRoles") String[] nameRoles) {
        if (bindingResult.hasErrors()) {
            return "new";
        }
        user.setRoles(roleService.getSetOfRoles(nameRoles));
        userService.saveUser(user);
        return "redirect:/users";
    }

//    @GetMapping("/users/{id}/edit")
//    public String editUser(Model model, @PathVariable("id") Long id) {
//        model.addAttribute("user", userService.getUserById(id));
//        return "edit";
//    }
//
//    @PatchMapping("/users/{id}")
//    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
//                         @PathVariable("id") Long id, @RequestParam(value = "nameRoles") String[] nameRoles) {
//        if (bindingResult.hasErrors()) {
//            return "edit";
//        }
//        user.setRoles(roleService.getSetOfRoles(nameRoles));
//        userService.updateUser(id, user);
//        return "redirect:/users";
//    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
