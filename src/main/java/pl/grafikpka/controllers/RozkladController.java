package pl.grafikpka.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grafikpka.model.RodzajRozkladu;
import pl.grafikpka.model.TypRozkladu;
import pl.grafikpka.service.RozkladService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "manager")
public class RozkladController {

    private static final String ROZKLAD_UPDATE_FORM_URL = "manager/rodzRozkladow/updateRozklad";

    private final RozkladService rozkladService;

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
        model.addAttribute("rodzajRozkladu", rozkladService.getById(id));
        log.info("Id to Update: " + id);
        return ROZKLAD_UPDATE_FORM_URL;
    }

    @PostMapping("/rodzRozkladu")
    public String updateRozklad(String id, TypRozkladu typRozkladu,String linia,String brygada,String godzina,
          String miejsceZmiany,String pierwszaLinia  , Model model) {
        RodzajRozkladu rodzajRozkladu = rozkladService.getById(id);
        model.addAttribute("rodzajRozkladu",rodzajRozkladu);
        rodzajRozkladu.setId(id);
        rodzajRozkladu.setTypRozkladu(typRozkladu);
        rodzajRozkladu.setLinia(linia);
        rodzajRozkladu.setBrygada(brygada);
        rodzajRozkladu.setGodzina(godzina);
        rodzajRozkladu.setMiejsceZmiany(miejsceZmiany);
        rodzajRozkladu.setPierwszaLinia(pierwszaLinia);
        log.info("Updated id: " + rodzajRozkladu.getId());
        rozkladService.save(rodzajRozkladu);
        return "redirect:/manager/rozklads";
    }
}
