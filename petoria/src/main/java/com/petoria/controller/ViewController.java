package com.petoria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("username", auth.getName());
        }
        return "index";
    }

    @GetMapping("/get-a-pet")
    public String getAPetPage() {
        return "get-a-pet";
    }

    @GetMapping("/lost-and-found")
    public String lostAndFoundPage() {
        return "lost-and-found";
    }

    @GetMapping("/lost-and-found/{id}")
    public String lostAndFoundDetailPage() {
        return "lost-and-found-detail";
    }

    @GetMapping("/blog")
    public String blogPage() {
        return "blog";
    }

    @GetMapping("/services")
    public String servicePage() {
        return "services";
    }

    @GetMapping("/message")
    public String messagePage() {
        return "message";
    }

    @GetMapping("/profile")
    public String profilePage() {
        return "profile";
    }

    @GetMapping("/settings")
    public String settingsPage() {
        return "settings";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/pet/{id}")
    public String petDetailPage() {
        return "pet-detail";
    }
}
