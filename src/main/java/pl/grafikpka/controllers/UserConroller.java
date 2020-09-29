package pl.grafikpka.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.grafikpka.model.Schedule;
import pl.grafikpka.service.ScheduleService;
import pl.grafikpka.service.UserService;

import java.util.Set;
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("users")
public class UserConroller {

    private final UserService userService;
    private final ScheduleService scheduleService;

    @GetMapping("/index")
    public String listUsers(@AuthenticationPrincipal UserDetails user, Model model){
        model.addAttribute("schedule", new Schedule());
        Set<Schedule> schedules = scheduleService.findSchedulesByDate(user.getUsername());
        model.addAttribute("schedules", schedules);
        return "users/index";
    }
    @GetMapping("/workDay")
    public String schowDriverWorkByUsername(@AuthenticationPrincipal UserDetails user, Model model){
        model.addAttribute("schedule", new Schedule());
        Set<Schedule> schedules = scheduleService.findSchedulesByUsername(user.getUsername());
        model.addAttribute("schedules", schedules);
        return "users/workDay";
    }
}
