package pl.grafikpka.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grafikpka.model.Schedule;
import pl.grafikpka.service.ScheduleService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping(value = "manager")
public class ScheduleController {

    private static final   String SCHEDULE_SCHEDULEFORM_URL = "manager/schedule/scheduleform";
    private ScheduleService scheduleService;


    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping(value = "/listschedules")
    public String home(Model model) {
        model.addAttribute("schedule", new Schedule());
        List<Schedule> schedules = scheduleService.findAll();
        model.addAttribute("schedules", schedules);

        return "manager/listschedules";
    }

    @PostMapping(value = "/fileupload")
    public String uploadFile(@ModelAttribute Schedule schedule, RedirectAttributes redirectAttributes) {
        boolean isFlag = scheduleService.saveDataFromCsv(schedule.getFile(), schedule.getDate(), schedule.getTypRozkladu());
        log.info("Wczytanie danych" + schedule.getDate());
        if (isFlag) {
           // redirectAttributes.addAttribute("date", schedule.getDate());
            redirectAttributes.addFlashAttribute("succesmessage", "File Upload Successfully");
        } else {
            redirectAttributes.addFlashAttribute("errormessage", "File Upload not done, Please try again");
        }
        return "redirect:/manager/listschedules";
    }
    @GetMapping("listschedules/{id}/delete")
    public String deleteById(@PathVariable String id){

        log.debug("Deleting id: " + id);

        scheduleService.deleteById(id);
        return "redirect:/manager/listschedules";
    }
    @GetMapping("/schedule/{id}/update")
    public String update(@PathVariable String id, Model model){
        model.addAttribute("schedule", scheduleService.findById(id));
        log.info("Id to Update: " + id);
        return SCHEDULE_SCHEDULEFORM_URL;
    }

    @PostMapping("schedule")
    public String updateSchedule(@Valid  Schedule schedule,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            schedule.setId(schedule.getId());
            schedule.setDate(schedule.getDate());
            schedule.setTypRozkladu(schedule.getTypRozkladu());
            schedule.setUsername(schedule.getUsername());
            schedule.setLinia(schedule.getLinia());
            schedule.setPoczatekPracy(schedule.getPoczatekPracy());
            schedule.setKoniecPracy(schedule.getKoniecPracy());
            schedule.setMiejsceZmiany(schedule.getMiejsceZmiany());
            return SCHEDULE_SCHEDULEFORM_URL;
        }

        scheduleService.save(schedule);
        model.addAttribute("schedule", scheduleService.findAll());
        log.info("Updated id: " + schedule.getId());
        return "redirect:/manager/listschedules";
    }

    @GetMapping("/schedule/busDriver/{username}")
    public String schowDriverWork(@PathVariable("username") String username, Model model){
        model.addAttribute("schedule", new Schedule());
       Set<Schedule> schedules = scheduleService.findSchedulesByUsename(username);
        model.addAttribute("schedules", schedules);

        return "manager/schedule/workDay";
    }
}

