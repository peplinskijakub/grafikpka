package pl.grafikpka.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.grafikpka.service.UserService;

@RequiredArgsConstructor
@Controller
@RequestMapping("users")
public class UserConroller {

    private final UserService userService;

    @GetMapping("/usersOptions")
    public String getUsers() {
        return "users/usersOptions";
    }
}
