//package web.controller;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//public class LoginController {
//
//    @GetMapping(value = "/")
//    public String getHomePage() {
//        return "indexing";
//    }
//
//    @RequestMapping(value = "/hello", method = RequestMethod.GET)
//    public String printWelcome(ModelMap model, Principal principal) {
//        List<String> messages = new ArrayList<>();
//        messages.add("Hello!");
//        messages.add("I'm Spring MVC-SECURITY application");
//        messages.add("5.5.1 version");
//        messages.add(principal.getName());
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        messages.add(authentication.toString());
//        messages.add(authentication.getName());
//
//        model.addAttribute("messages", messages);
//        return "hello";
//    }
//
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String loginPage(ModelMap model, HttpServletRequest request) {
//        if (request.getParameterMap().containsKey("Error")){
//            model.addAttribute("Error", true);
//        }
//        return "login";
//    }
//}
