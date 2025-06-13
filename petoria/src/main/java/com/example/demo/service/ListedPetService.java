package com.example.demo.service;


import com.example.demo.dto.ListedPetDto;
import com.example.demo.model.ListedPet;
import com.example.demo.repository.ListedPetRepository;
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
public class ListedPetService {

    private final ListedPetRepository repository;

    public Page<ListedPet> getAllPetsPaged(int page, int size) {
        return repository.findAllByOrderBySubmissionTimeDesc(PageRequest.of(page, size));
    }

//    public List<ListedPet> getAllPets(int page, String sortBy) {
//        Pageable pageable;
//        switch (sortBy) {
//            case "priceAsc":
//                pageable = PageRequest.of(page, 9, Sort.by("price").ascending());
//                break;
//            case "priceDesc":
//                pageable = PageRequest.of(page, 9, Sort.by("price").descending());
//                break;
//            case "dog":
//            case "cat":
//            case "bird":
//            case "other":
//                pageable = PageRequest.of(page, 9, Sort.by("submissionTime").descending());
//                return (List<ListedPet>) repository.findByType(sortBy, pageable);
//            default:
//                pageable = PageRequest.of(page, 9, Sort.by("submissionTime").descending());
//        }
//
//        return repository.findAll(pageable).getContent();
//    }

    public List<ListedPetDto> getAllPets(int page, String sort) {
        Pageable pageable;
        switch (sort) {
            case "priceAsc":
                pageable = PageRequest.of(page, 9, Sort.by("price").ascending());
                break;
            case "priceDesc":
                pageable = PageRequest.of(page, 9, Sort.by("price").descending());
                break;
            case "dog":
            case "cat":
            case "bird":
            case "other":
                pageable = PageRequest.of(page, 9, Sort.by("submissionTime").descending());
                return repository.findByTypeIgnoreCase(sort, pageable)
                        .stream().map(this::mapToDto).toList();
            default:
                pageable = PageRequest.of(page, 9, Sort.by("submissionTime").descending());
        }

        return repository.findAll(pageable)
                .stream().map(this::mapToDto).toList();
    }

    private ListedPetDto mapToDto(ListedPet pet) {
        ListedPetDto dto = new ListedPetDto();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setPrice(pet.getPrice());
        dto.setDescription(pet.getDescription());
        dto.setPhotoUrl(pet.getPhotoUrl());
        dto.setType(pet.getType());
//        dto.setSubmissionTime(pet.getSubmissionTime());
        return dto;
    }

    public ListedPetDto addPet(ListedPetDto dto) {
        ListedPet pet = new ListedPet();
        pet.setName(dto.getName());
        pet.setPrice(dto.getPrice());
        pet.setDescription(dto.getDescription());
        pet.setPhotoUrl(dto.getPhotoUrl());
        pet.setType(dto.getType());
        pet.setSubmissionTime(LocalDateTime.now());

        repository.save(pet);
        return dto;
    }

    public ListedPet getPetById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Pet not found"));
    }
}
