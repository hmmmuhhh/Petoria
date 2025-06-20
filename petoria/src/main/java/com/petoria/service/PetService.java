package com.petoria.service;


import com.petoria.dto.PetDto;
import com.petoria.model.Pet;
import com.petoria.model.PetType;
import com.petoria.repository.PetRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository repository;

//    public Page<Pet> getAllPetsPaged(int page, int size) {
//        return repository.findAllByOrderBySubmissionTimeDesc(PageRequest.of(page, size));
//    }

    public Page<PetDto> getAllPetsWithPagination(String sort, Pageable pageable) {
        // Optional: add sort logic here if needed
        return repository.findAll(pageable)
                .map(this::mapToDto);
    }

    public List<PetDto> getAllPets(int page, String sort) {
        Pageable pageable;

        if (PetType.isValid(sort)) {
            pageable = PageRequest.of(page, 9, Sort.by("submissionTime").descending());
            return repository.findByTypeIgnoreCase(sort, pageable)
                    .stream().map(this::mapToDto).toList();
        }

        pageable = PageRequest.of(page, 9, Sort.by("submissionTime").descending());
        return repository.findAll(pageable)
                .stream().map(this::mapToDto).toList();
    }

    private PetDto mapToDto(Pet pet) {
        PetDto dto = new PetDto();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setPrice(pet.getPrice());
        dto.setDescription(pet.getDescription());
        dto.setPhotoUrl(pet.getPhotoUrl());
        dto.setType(String.valueOf(pet.getType()));
        dto.setSubmissionTime(pet.getSubmissionTime());
        return dto;
    }

    public Pet addPet(PetDto dto, Long userId) {
        Pet pet = new Pet();
        pet.setName(dto.getName());
        pet.setPrice(dto.getPrice());
        pet.setDescription(dto.getDescription());
        pet.setPhotoUrl(dto.getPhotoUrl());
        pet.setType(PetType.fromString(dto.getType()));
        pet.setSubmissionTime(LocalDateTime.now());
        pet.setUserId(userId);
        return repository.save(pet);
    }

    public Pet getPetById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Pet not found"));
    }
}
