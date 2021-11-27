package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserAnrRoleService;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class AdminController {

    private final UserAnrRoleService userAnrRoleService;

    @Autowired
    public AdminController(UserAnrRoleService userAnrRoleService) {
        this.userAnrRoleService = userAnrRoleService;
    }

    @GetMapping("/users")
    public String printAllUsers(Model model) {
        model.addAttribute("users", userAnrRoleService.getAllUsers());
        return "users";
    }

    @GetMapping("/users/{id}")
    public String printUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userAnrRoleService.getUserById(id));
        return "index";
    }

    @GetMapping("/users/new")
    public String createUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "new";
        }
        model.addAttribute("allRoles", userAnrRoleService.findAllRoles());
        userAnrRoleService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userAnrRoleService.getUserById(id));
        return "edit";
    }

    @PatchMapping("/users/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") Long id, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        model.addAttribute("allRoles", userAnrRoleService.findAllRoles());
        userAnrRoleService.updateUser(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userAnrRoleService.deleteUser(id);
        return "redirect:/users";
    }
}

