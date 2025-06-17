package com.petoria.controller;

import com.petoria.dto.ListedPetDto;
import com.petoria.model.ListedPet;
import com.petoria.security.CustomUserDetails;
import com.petoria.service.ListedPetService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ListedPet addPet(@RequestBody ListedPetDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You must be logged in to add a pet.");
        }

        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        Long userId = user.getId();

        System.out.println("AUTH: " + SecurityContextHolder.getContext().getAuthentication());
        System.out.println("TOKEN PRINCIPAL: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return service.addPet(dto, userId);
    }

    @GetMapping
    public ResponseEntity<List<ListedPetDto>> getAllPets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "newest") String sort
    ) {
        return ResponseEntity.ok(service.getAllPets(page, sort));
    }

}
