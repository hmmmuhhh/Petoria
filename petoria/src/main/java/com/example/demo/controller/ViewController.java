package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "index"; // points to templates/index.html
    }

    @GetMapping("/getapet")
    public String getAPetPage() {
        return "get-a-pet"; // templates/get-a-pet.html
    }

    @GetMapping("/pet/{id}")
    public String petDetailPage() {
        return "pet-detail";
    }
}
