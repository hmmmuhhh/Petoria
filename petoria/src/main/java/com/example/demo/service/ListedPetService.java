package com.example.demo.service;


import com.example.demo.dto.ListedPetDto;
import com.example.demo.model.ListedPet;
import com.example.demo.repository.ListedPetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ListedPetService {

    private final ListedPetRepository repository;

    public Page<ListedPet> getAllPetsPaged(int page, int size) {
        return repository.findAllByOrderBySubmissionTimeDesc(PageRequest.of(page, size));
    }

    public ListedPet addPet(ListedPetDto dto) {
        ListedPet pet = new ListedPet();
        pet.setName(dto.getName());
        pet.setDescription(dto.getDescription());
        pet.setPhotoUrl(dto.getPhotoUrl());
        pet.setPrice(dto.getPrice());
        pet.setSubmissionTime(LocalDateTime.now());
        return repository.save(pet);
    }

    public ListedPet getPetById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Pet not found"));
    }
}
