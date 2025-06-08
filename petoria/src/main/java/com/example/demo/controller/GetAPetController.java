package com.example.demo.controller;

import com.example.demo.dto.ListedPetDto;
import com.example.demo.model.ListedPet;
import com.example.demo.service.ListedPetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class GetAPetController {

    private final ListedPetService service;

    @GetMapping
    public List<ListedPet> getAllPets(@RequestParam(defaultValue = "0") int page) {
        return service.getAllPetsPaged(page, 9).getContent();
    }

    @GetMapping("/{id}")
    public ListedPet getPet(@PathVariable Long id) {
        return service.getPetById(id);
    }

    @PostMapping
    public ListedPet createPet(@RequestBody ListedPetDto dto) {
        return service.addPet(dto);
    }
}
