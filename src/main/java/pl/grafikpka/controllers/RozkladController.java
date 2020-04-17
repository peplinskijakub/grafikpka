package pl.grafikpka.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grafikpka.model.RodzajRozkladu;
import pl.grafikpka.service.RozkladService;

import java.util.List;

@Controller
@RequestMapping(value = "manager")
public class RozkladController {
    private RozkladService rozkladService;

    public RozkladController(RozkladService rozkladService) {
        this.rozkladService = rozkladService;
    }

    @GetMapping(value = "/rozklads")
    public String getrozklads(Model model) {
        model.addAttribute("rozklad", new RodzajRozkladu());
        List<RodzajRozkladu> rozklady = rozkladService.findAll();
        model.addAttribute("rozklady", rozklady);

        return "manager/rozklads";
    }

    @PostMapping(value = "/manager/addrozklad")
    public String addRozklad(@ModelAttribute RodzajRozkladu rodzajRozkladu, BindingResult result, RedirectAttributes redirectAttributes) {
        RodzajRozkladu isFlag = rozkladService.save(rodzajRozkladu);
        redirectAttributes.addFlashAttribute("message", "Failed");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        if (result.hasErrors()) {
            return "redirect:/manager/rozklads";
        }
        redirectAttributes.addFlashAttribute("message", "Success");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/manager/rozklads";
    }
}
