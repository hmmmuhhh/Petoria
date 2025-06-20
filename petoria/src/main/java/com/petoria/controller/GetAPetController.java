package com.petoria.controller;

import com.petoria.dto.PetDto;
import com.petoria.model.Pet;
import com.petoria.security.CustomUserDetails;
import com.petoria.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private final PetService service;

    @GetMapping("/{id}")
    public Pet getPet(@PathVariable Long id) {
        return service.getPetById(id);
    }

    @PostMapping
    public Pet addPet(@RequestBody PetDto dto) {
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
    public Page<PetDto> getAllPets(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "newest") String sort) {
        Pageable pageable = PageRequest.of(page, 9, Sort.by("submissionTime").descending());
        return service.getAllPetsWithPagination(sort, pageable);
    }

}
