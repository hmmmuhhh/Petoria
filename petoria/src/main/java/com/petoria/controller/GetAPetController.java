package com.petoria.controller;

import com.petoria.dto.PetDto;
import com.petoria.dto.PetResponseDto;
import com.petoria.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class GetAPetController {

    private final PetService petService;

    @PostMapping
    public ResponseEntity<Void> createPet(@AuthenticationPrincipal UserDetails userDetails,
                                          @ModelAttribute PetDto dto) throws IOException {
        petService.createPet(userDetails.getUsername(), dto);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<Page<PetResponseDto>> getPets(@AuthenticationPrincipal UserDetails user,
                                                        @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(petService.getAllPets(user.getUsername(), page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDto> getPetById(@PathVariable Long id,
                                                     @AuthenticationPrincipal UserDetails currentUserDetails) {
        PetResponseDto pet = petService.getPetById(id, currentUserDetails);
        return ResponseEntity.ok(pet);
    }

    @PutMapping("/{id}/mark-sold")
    public ResponseEntity<Void> markAsSold(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) throws AccessDeniedException {
        petService.toggleSoldStatus(id, user.getUsername());
        return ResponseEntity.ok().build();
    }
}
