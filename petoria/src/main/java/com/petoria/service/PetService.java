package com.petoria.service;


import com.petoria.dto.PetDto;
import com.petoria.model.Pet;
import com.petoria.model.PetType;
import com.petoria.model.User;
import com.petoria.repository.PetRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository repository;

    public Page<PetDto> getAllPetsWithPagination(String sort, Pageable pageable) {
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
        return PetDto.builder()
                .id(pet.getId())
                .name(pet.getName())
                .price(pet.getPrice())
                .description(pet.getDescription())
                .photoUrl(pet.getPhotoUrl())
                .isSold(pet.isSold())
                .type(String.valueOf(pet.getType()))
                .submissionTime(pet.getSubmissionTime())
                .authorUsername(pet.getCreator().getUsername())
                .authorProfilePicUrl(pet.getCreator().getProfilePicUrl())
                .build();
    }

    public PetDto addPet(PetDto dto, User user) {
        Pet pet = Pet.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .photoUrl(dto.getPhotoUrl())
                .type(PetType.fromString(dto.getType()))
                .submissionTime(LocalDateTime.now())
                .creator(user)
                .build();

        return mapToDto(repository.save(pet));
    }
    public PetDto getPet(Long id) {
        Pet dto = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found"));

        return PetDto.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .photoUrl(dto.getPhotoUrl())
                .type(String.valueOf(dto.getType()))
                .submissionTime(LocalDateTime.now())
                .isSold(dto.isSold())
                .authorUsername(dto.getCreator().getUsername())
                .authorProfilePicUrl(dto.getCreator().getProfilePicUrl())
                .build();
    }


    public Pet getPetById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Pet not found"));
    }
}
