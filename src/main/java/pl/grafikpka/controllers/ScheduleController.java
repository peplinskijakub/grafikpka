package pl.grafikpka.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grafikpka.model.Schedule;
import pl.grafikpka.service.ScheduleService;

import java.util.List;
@Slf4j
@Controller
public class ScheduleController {

    private ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping(value = "/listschedules")
    public String home(Model model) {
        model.addAttribute("schedule", new Schedule());
        List<Schedule> schedules = scheduleService.findAll();
        model.addAttribute("schedules", schedules);

        return "/listschedules";
    }

    @PostMapping(value = "/fileupload")
    public String uploadFile(@ModelAttribute Schedule schedule, RedirectAttributes redirectAttributes) {
        boolean isFlag = scheduleService.saveDataFromCsv(schedule.getFile(), schedule.getDate(), String.valueOf(schedule.getRodzajRozkladu()));
        log.info("Wczytanie danych" + schedule.getDate());
        if (isFlag) {
            redirectAttributes.addAttribute("date", schedule.getDate());
            redirectAttributes.addFlashAttribute("succesmessage", "File Upload Successfully");
        } else {
            redirectAttributes.addFlashAttribute("errormessage", "File Upload not done, Please try again");
        }
        return "redirect:/listschedules";
    }
    @GetMapping("listschedules/{id}/delete")
    public String deleteById(@PathVariable String id){

        log.debug("Deleting id: " + id);

        scheduleService.deleteById(id);
        return "redirect:/";
    }
}

