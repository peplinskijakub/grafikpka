package pl.grafikpka.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.grafikpka.model.Schedule;
import pl.grafikpka.service.ScheduleService;
import pl.grafikpka.service.UserService;

import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("users")
public class UserConroller {

    private final UserService userService;
    private final ScheduleService scheduleService;

    @GetMapping("/index")
    public String listUsers(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("schedule", new Schedule());
        Set<String> schedules = scheduleService.findSchedulesByDate(user.getUsername());
        model.addAttribute("schedules", schedules);
        return "users/index";
    }

    @GetMapping("/workDay/{date}")
    public String showDriverDaylyWork(@AuthenticationPrincipal UserDetails user, @PathVariable String date, Model model) {
        model.addAttribute("schedule", new Schedule());
        List<Schedule> schedules = scheduleService.findSchedulesByUsernameAndDate(user.getUsername(),date);
        model.addAttribute("schedules", schedules);
        return "users/workDay";
    }
}
