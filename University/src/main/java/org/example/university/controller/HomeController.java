package org.example.university.controller;

import org.example.university.Service.RegistrationService;
import org.example.university.Service.WorkshopService;
import org.example.university.Repository.UserRepository;
import org.example.university.model.Registration;
import org.example.university.model.User;
import org.example.university.model.Workshop;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    private final WorkshopService workshopService;
    private final UserRepository userRepository;
    private final RegistrationService registrationService;

    public HomeController(WorkshopService workshopService,
                          UserRepository userRepository,
                          RegistrationService registrationService) {
        this.workshopService = workshopService;
        this.userRepository = userRepository;
        this.registrationService = registrationService;
    }

    // GET /
    @GetMapping("/")
    public String home(Model model) {
        List<Workshop> workshops = workshopService.getAllWorkshops();
        model.addAttribute("workshops", workshops);
        return "index";
    }

    // GET /workshops/{id}
    @GetMapping("/workshops/{id}")
    public String workshopDetail(@PathVariable Long id,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 Model model) {
        Workshop workshop = workshopService.getWorkshopById(id);
        model.addAttribute("workshop", workshop);

        if (userDetails != null) {
            User user = userRepository.findByEmail(userDetails.getUsername())
                    .orElse(null);
            if (user != null) {
                List<Registration> myRegs = registrationService
                        .getUserRegistrations(user.getId());
                boolean alreadyRegistered = myRegs.stream()
                        .anyMatch(r -> r.getWorkshop().getId().equals(id)
                                && r.getStatus().equals("ACTIVE"));
                model.addAttribute("alreadyRegistered", alreadyRegistered);
                model.addAttribute("userId", user.getId());
            }
        }

        return "workshop-detail";
    }
}