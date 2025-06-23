package com.petoria.controller;

import com.petoria.dto.PetDto;
import com.petoria.model.Pet;
import com.petoria.model.User;
import com.petoria.security.CustomUserDetails;
import com.petoria.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class GetAPetController {

    private final PetService service;

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> getPet(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPet(id));
    }

    @PostMapping
    public PetDto addPet(@RequestBody PetDto dto) {
        User user = getAuthenticatedUser();
        return service.addPet(dto, user);
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = new User();
        user.setId(userDetails.getId());
        return user;
    }

    @GetMapping
    public Page<PetDto> getAllPets(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "newest") String sort) {
        Pageable pageable = PageRequest.of(page, 9, Sort.by("submissionTime").descending());
        return service.getAllPetsWithPagination(sort, pageable);
    }

}
