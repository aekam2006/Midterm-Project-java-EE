package org.example.university.controller;

import org.example.university.Service.RegistrationService;
import org.example.university.Service.WorkshopService;
import org.example.university.dto.request.WorkshopRequestDTO;
import org.example.university.mapper.WorkshopMapper;
import org.example.university.model.Registration;
import org.example.university.model.Workshop;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminWebController {

    private final WorkshopService workshopService;
    private final RegistrationService registrationService;
    private final WorkshopMapper workshopMapper;

    public AdminWebController(WorkshopService workshopService,
                              RegistrationService registrationService,
                              WorkshopMapper workshopMapper) {
        this.workshopService = workshopService;
        this.registrationService = registrationService;
        this.workshopMapper = workshopMapper;
    }

    // GET /admin/workshops
    @GetMapping("/workshops")
    public String manageWorkshops(Model model) {
        model.addAttribute("workshops", workshopService.getAllWorkshops());
        return "admin/workshops";
    }

    // GET /admin/workshops/new
    @GetMapping("/workshops/new")
    public String newWorkshopForm(Model model) {
        model.addAttribute("workshopForm", new WorkshopRequestDTO());
        model.addAttribute("workshopId", null);
        return "admin/workshop-form";
    }

    // POST /admin/workshops/new
    @PostMapping("/workshops/new")
    public String createWorkshop(@ModelAttribute WorkshopRequestDTO workshopForm,
                                 RedirectAttributes redirectAttributes) {
        try {
            Workshop workshop = workshopMapper.toEntity(workshopForm);
            workshopService.createWorkshop(workshop);
            redirectAttributes.addFlashAttribute("success",
                    "Workshop created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/workshops";
    }

    // GET /admin/workshops/{id}/edit
    @GetMapping("/workshops/{id}/edit")
    public String editWorkshopForm(@PathVariable Long id, Model model) {
        Workshop workshop = workshopService.getWorkshopById(id);
        WorkshopRequestDTO form = workshopMapper.toRequestDTO(workshop);
        model.addAttribute("workshopForm", form);
        model.addAttribute("workshopId", id);
        return "admin/workshop-form";
    }

    // POST /admin/workshops/{id}/edit
    @PostMapping("/workshops/{id}/edit")
    public String updateWorkshop(@PathVariable Long id,
                                 @ModelAttribute WorkshopRequestDTO workshopForm,
                                 RedirectAttributes redirectAttributes) {
        try {
            Workshop workshop = workshopMapper.toEntity(workshopForm);
            workshopService.updateWorkshop(id, workshop);
            redirectAttributes.addFlashAttribute("success",
                    "Workshop updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/workshops";
    }

    // POST /admin/workshops/{id}/cancel
    @PostMapping("/workshops/{id}/cancel")
    public String cancelWorkshop(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes) {
        try {
            workshopService.cancelWorkshop(id);
            redirectAttributes.addFlashAttribute("success",
                    "Workshop cancelled successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/workshops";
    }

    // GET /admin/workshops/{id}/registrations
    @GetMapping("/workshops/{id}/registrations")
    public String viewRegistrations(@PathVariable Long id, Model model) {
        Workshop workshop = workshopService.getWorkshopById(id);
        List<Registration> registrations = registrationService
                .getWorkshopRegistrations(id);
        model.addAttribute("workshop", workshop);
        model.addAttribute("registrations", registrations);
        return "admin/registrations";
    }
}