package pl.grafikpka.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grafikpka.model.Schedule;
import pl.grafikpka.service.ScheduleService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
public class ScheduleController {

    private static final   String SCHEDULE_SCHEDULEFORM_URL = "schedule/scheduleform";
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
        boolean isFlag = scheduleService.saveDataFromCsv(schedule.getFile(), schedule.getDate(), schedule.getTypRozkladu());
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
        return "redirect:/listschedules";
    }
    @GetMapping("schedule/{id}/update")
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
            schedule.setNrSluzbowy(schedule.getNrSluzbowy());
            schedule.setLinia(schedule.getLinia());
            schedule.setPoczatekPracy(schedule.getPoczatekPracy());
            schedule.setKoniecPracy(schedule.getKoniecPracy());
            schedule.setMiejsceZmiany(schedule.getMiejsceZmiany());
            return SCHEDULE_SCHEDULEFORM_URL;
        }

        scheduleService.save(schedule);
        model.addAttribute("schedule", scheduleService.findAll());
        log.info("Updated id: " + schedule.getId());
        return "redirect:/listschedules";
    }

    @GetMapping("/busDriver/{nrSluzbowy}")
    public String schowDriverWork(@PathVariable("nrSluzbowy") String nrSluzbowy, Model model){
        model.addAttribute("schedule", new Schedule());
       Set<Schedule> schedules = scheduleService.findScheduleByNrSluzbowy(nrSluzbowy);
        model.addAttribute("schedules", schedules);

        return "/schedule/workDay";
    }
}

