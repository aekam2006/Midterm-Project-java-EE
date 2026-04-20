package org.example.university.controller;

import org.example.university.Service.RegistrationService;
import org.example.university.Repository.UserRepository;
import org.example.university.model.Registration;
import org.example.university.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RegistrationWebController {

    private final RegistrationService registrationService;
    private final UserRepository userRepository;

    public RegistrationWebController(RegistrationService registrationService,
                                     UserRepository userRepository) {
        this.registrationService = registrationService;
        this.userRepository = userRepository;
    }

    // GET /my/registrations
    @GetMapping("/my/registrations")
    public String myRegistrations(@AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Registration> registrations = registrationService
                .getUserRegistrations(user.getId());
        model.addAttribute("registrations", registrations);
        return "my-registrations";
    }

    // POST /workshops/{id}/register
    @PostMapping("/workshops/{id}/register")
    public String register(@PathVariable Long id,
                           @AuthenticationPrincipal UserDetails userDetails,
                           RedirectAttributes redirectAttributes) {
        try {
            User user = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            registrationService.register(user.getId(), id);
            redirectAttributes.addFlashAttribute("success",
                    "Successfully registered for workshop!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/workshops/" + id;
    }

    // POST /registrations/{id}/cancel
    @PostMapping("/registrations/{id}/cancel")
    public String cancel(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        try {
            registrationService.cancelRegistration(id);
            redirectAttributes.addFlashAttribute("success",
                    "Registration cancelled successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/my/registrations";
    }
}