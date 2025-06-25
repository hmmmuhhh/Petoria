package com.petoria.service;
import com.petoria.dto.PetDto;
import com.petoria.dto.PetResponseDto;
import com.petoria.model.Pet;
import com.petoria.model.User;
import com.petoria.repository.PetRepository;
import com.petoria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    private final String uploadDir = "uploads/";

    public void createPet(String username, PetDto dto) throws IOException {
        User user = userRepository.findByEmailOrUsername(username, username).orElseThrow();

        List<String> photoPaths = saveFiles(dto.getPhotos());
        System.out.println("DTO values: name=" + dto.getName() + ", price=" + dto.getPrice() + ", description=" + dto.getDescription());

        System.out.println("DTO name: " + dto.getName());
        System.out.println("DTO desc: " + dto.getDescription());
        System.out.println("DTO price: " + dto.getPrice());
        System.out.println("DTO type: " + dto.getType());

        Pet pet = new Pet();
        pet.setName(dto.getName());
        pet.setType(dto.getType());
        pet.setDescription(dto.getDescription());
        pet.setPrice(dto.getPrice());
        pet.setSold(false);
        pet.setPhotoPaths(photoPaths);
        pet.setSubmissionTime(LocalDateTime.now());
        pet.setUser(user);


        petRepository.save(pet);
    }

    public Page<PetResponseDto> getAllPets(String currentUsername, int page) {
        User currentUser = userRepository.findByEmailOrUsername(currentUsername, currentUsername).orElse(null);
        Pageable pageable = PageRequest.of(page, 9, Sort.by("id").descending());

        return petRepository.findAll(pageable).map(pet -> {
            User poster = pet.getUser();
            return new PetResponseDto(
                    pet.getId(),
                    pet.getName(),
                    pet.getType(),
                    pet.getPrice(),
                    pet.getDescription(),
                    pet.getPhotoPaths(),
                    pet.isSold(),
                    poster.getUsername(),
                    poster.getProfilePicUrl(),
                    currentUser != null && poster.getId().equals(currentUser.getId())
            );
        });
    }

    public PetResponseDto getPetById(Long id, UserDetails currentUserDetails) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        User poster = pet.getUser();

        boolean isOwner = false;
        if (currentUserDetails != null && poster.getUsername().equals(currentUserDetails.getUsername())) {
            isOwner = true;
        }

        return new PetResponseDto(
                pet.getId(),
                pet.getName(),
                pet.getType(),
                pet.getPrice(),
                pet.getDescription(),
                pet.getPhotoPaths(),
                pet.isSold(),
                poster.getUsername(),
                poster.getProfilePicUrl(),
                isOwner
        );
    }

    public void toggleSoldStatus(Long petId, String username) throws AccessDeniedException {
        Pet pet = petRepository.findById(petId).orElseThrow();
        if (!pet.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("Not your listing");
        }
        pet.setSold(!pet.isSold());
        petRepository.save(pet);
    }

    private List<String> saveFiles(List<MultipartFile> files) throws IOException {
        List<String> paths = new ArrayList<>();
        if (files == null) return paths;

        File folder = new File(uploadDir);
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (!created) throw new IOException("Upload folder could not be created");
        }

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            paths.add("/media/uploads/" + fileName);
        }
        return paths;
    }
}
