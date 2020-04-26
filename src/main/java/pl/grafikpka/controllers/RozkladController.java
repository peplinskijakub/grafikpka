package pl.grafikpka.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grafikpka.model.RodzajRozkladu;
import pl.grafikpka.service.RozkladService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "manager")
public class RozkladController {

    private static final String ROZKLAD_UPDATE_FORM_URL = "manager/rodzRozkladow/updateRozklad";

    private RozkladService rozkladService;

    public RozkladController(RozkladService rozkladService) {
        this.rozkladService = rozkladService;
    }

    @GetMapping(value = "/rozklads")
    public String getRozklads(Model model) {
        model.addAttribute("rozklad", new RodzajRozkladu());
        List<RodzajRozkladu> rozklady = rozkladService.findAll();
        model.addAttribute("rozklady", rozklady);

        return "manager/rozklads";
    }

    @PostMapping(value = "/addRozklad")
    public String addRozklad(@ModelAttribute RodzajRozkladu rodzajRozkladu, RedirectAttributes redirectAttributes) {
        boolean isFlag = rozkladService.saveRozklad(
                rodzajRozkladu.getTypRozkladu()
                , rodzajRozkladu.getLinia()
                , rodzajRozkladu.getBrygada()
                , rodzajRozkladu.getGodzina()
                , rodzajRozkladu.getMiejsceZmiany()
                , rodzajRozkladu.getPierwszaLinia());
        if (isFlag) {
            redirectAttributes.addFlashAttribute("succesmessage", "File Upload Successfully");
        } else {
            redirectAttributes.addFlashAttribute("errormessage", "File Upload not done, Please try again");
        }
        return "redirect:/manager/rozklads";
    }

    @GetMapping("rozklads/{id}/delete")
    public String deleteById(@PathVariable String id) {

        log.debug("Deleting id: " + id);

        rozkladService.deleteById(id);
        return "redirect:/manager/rozklads";
    }

    @GetMapping("/rodzRozkladow/{id}/update")
    public String update(@PathVariable String id, Model model) {
        model.addAttribute("rodzajRozkladu", rozkladService.findById(id));
        log.info("Id to Update: " + id);
        return ROZKLAD_UPDATE_FORM_URL;
    }

    @PostMapping("/rodzRozkladu")
    public String updateRozklad(@Valid RodzajRozkladu rodzajRozkladu, BindingResult result, Model model) {
        if (result.hasErrors()) {
            rodzajRozkladu.setId(rodzajRozkladu.getId());
            rodzajRozkladu.setTypRozkladu(rodzajRozkladu.getTypRozkladu());
            rodzajRozkladu.setLinia(rodzajRozkladu.getLinia());
            rodzajRozkladu.setBrygada(rodzajRozkladu.getBrygada());
            rodzajRozkladu.setGodzina(rodzajRozkladu.getGodzina());
            rodzajRozkladu.setMiejsceZmiany(rodzajRozkladu.getMiejsceZmiany());
            rodzajRozkladu.setPierwszaLinia(rodzajRozkladu.getPierwszaLinia());
            return ROZKLAD_UPDATE_FORM_URL;
        }
        rozkladService.save(rodzajRozkladu);
        model.addAttribute("rozklad", rozkladService.findAll());
        log.info("Updated id: " + rodzajRozkladu.getId());
        return "redirect:/manager/rozklads";
    }
}
