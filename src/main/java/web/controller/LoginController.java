package web.controller;

import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.model.User;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @GetMapping(value = "/")
    public String getHomePage(Model model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("5.5.1 version");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String printWelcome(@CurrentSecurityContext(expression = "authentication.principal") User principal,
                               Model model) {
        model.addAttribute("user", principal);
        return "user";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }
}

