package pl.grafikpka.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import pl.grafikpka.model.User;
import pl.grafikpka.service.UserService;

import javax.validation.Valid;
import java.io.IOException;


@Controller
@RequiredArgsConstructor
@RequestMapping("manager/users")
public class RegisterController {
     private final UserService userService;

    @GetMapping("/index")
    public String index() {
        return "users/index";
    }

    @GetMapping("/usersOptions")
    public String usersOptions(){
        return "manager/users/usersOptions";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "manager/users/registerForm";
    }
    @GetMapping("/registerFromFile")
    public String registerFromFile(Model model) {
        model.addAttribute("user", new User());
        return "manager/users/registerFromFile";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "manager/users/registerForm";
        }
        if (userService.isUserPresent(user.getUsername())) {
            model.addAttribute("exist", true);
            return "manager/users/registerForm";
        }
        userService.createUser(user);
        return "/success";

    }
    @PostMapping("/registerFromFile")
    public String registerUsersFromFile(MultipartFile file, Model model) throws IOException {
       userService.createUsersFromFile(file);
            model.addAttribute("exist", true);
        return "manager/users/usersOptions";
    }

}

