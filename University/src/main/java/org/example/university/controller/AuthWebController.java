package org.example.university.controller;

import org.example.university.Service.AuthService;
import org.example.university.dto.request.UserRequestDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthWebController {

    private final AuthService authService;

    public AuthWebController(AuthService authService) {
        this.authService = authService;
    }

    // GET /login
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // GET /register
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userForm", new UserRequestDTO());
        return "register";
    }

    // POST /register
    @PostMapping("/register")
    public String register(@ModelAttribute UserRequestDTO userForm,
                           RedirectAttributes redirectAttributes) {
        try {
            userForm.setRole("ATTENDEE");
            authService.register(userForm);
            redirectAttributes.addFlashAttribute("success",
                    "Account created successfully! Please login.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }
}