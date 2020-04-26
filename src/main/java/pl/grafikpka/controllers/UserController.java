package pl.grafikpka.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.grafikpka.model.User;
import pl.grafikpka.service.UserService;

import javax.validation.Valid;


@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @GetMapping("/index")
    public String index() {
        return "users/index";
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user",new User());
        return "manager/registerForm";
    }
    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "manager/registerForm";
        }
        if(userService.isUserPresent(user.getUsername())) {
            model.addAttribute("exist",true);

            return "manager/registerForm";

        }
        userService.createUser(user);

        return "/success";

    }

}

