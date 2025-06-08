package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/getapet")
    public String getAPetPage() {
        return "get-a-pet";
    }

    @GetMapping("/pet/{id}")
    public String petDetailPage() {
        return "pet-detail";
    }

    @GetMapping("/lost-and-found")
    public String lostAndFoundPage() {
        return "lost-and-found";
    }
}
