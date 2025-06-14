package com.petoria.controller;

import com.petoria.dto.ListedPetDto;
import com.petoria.model.ListedPet;
import com.petoria.service.ListedPetService;
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
    public ResponseEntity<ListedPetDto> addPet(@RequestBody ListedPetDto dto) {
        return ResponseEntity.ok(service.addPet(dto));
    }

    @GetMapping
    public ResponseEntity<List<ListedPetDto>> getAllPets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "newest") String sort
    ) {
        return ResponseEntity.ok(service.getAllPets(page, sort));
    }

}
