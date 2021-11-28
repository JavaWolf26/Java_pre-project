package web.controller;

import org.hibernate.AssertionFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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

    @GetMapping("/users/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PatchMapping("/users/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") Long id, @RequestParam(value = "nameRoles") String[] nameRoles) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        user.setRoles(roleService.getSetOfRoles(nameRoles));
        userService.updateUser(id, user);
        return "redirect:/users";
    }

//    @GetMapping("/{id}/edit")
//    public String editUserForm(@PathVariable(value = "id", required = true) Long userId, Model model) {
//        try {
//            model.addAttribute("user", appService.findUser(userId));
//        } catch (EmptyResultDataAccessException e) {
//            e.printStackTrace();
//
//            return "redirect:/admin";
//        }
//        model.addAttribute("allRoles", appService.findAllRoles());
//
//        return "user-form";
//    }
//
//    @GetMapping(value = "/new")
//    public String addUserForm(@ModelAttribute("user") User user, Model model) {
//        model.addAttribute("allRoles", appService.findAllRoles());
//
//        return "user-form";
//    }
//
//    @PostMapping()
//    public String saveOrUpdateUser(@Valid @ModelAttribute("user") User user,
//                                   BindingResult bindingResult, Model model) {
//        // Непонятно как избавиться от этого
//        // Поймать в сервисе транзакционный эксепшн нельзя
//        try {
//            return appService.saveUser(user, bindingResult, model) ? "redirect:/admin" : "user-form";
//        } catch (AssertionFailure | UnexpectedRollbackException e) {
//            return "user-form";
//        }
//    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}

