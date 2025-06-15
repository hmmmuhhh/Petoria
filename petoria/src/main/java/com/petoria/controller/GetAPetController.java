package com.petoria.controller;

import com.petoria.dto.ListedPetDto;
import com.petoria.model.ListedPet;
import com.petoria.service.ListedPetService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class GetAPetController {

    private final ListedPetService service;

    @GetMapping("/{id}")
    public ListedPet getPet(@PathVariable Long id) {
        return service.getPetById(id);
    }

    @PostMapping
    public ListedPet addPet(@RequestBody ListedPetDto dto, HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            throw new IllegalStateException("User must be logged in to add pets.");
        }
//        return ResponseEntity.ok(service.addPet(dto));
        return service.addPet(dto, Long.parseLong(userId.toString()));
    }

    @GetMapping
    public ResponseEntity<List<ListedPetDto>> getAllPets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "newest") String sort
    ) {
        return ResponseEntity.ok(service.getAllPets(page, sort));
    }

}
