package pl.grafikpka.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import pl.grafikpka.model.User;


@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/index")
    public String index() {
        return "users/index";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

}

