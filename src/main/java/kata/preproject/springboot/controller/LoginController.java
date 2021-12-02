package kata.preproject.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @GetMapping(value = "/")
    public String getWelcome(Model model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("5.5.1 version");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }
}

