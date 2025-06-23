package com.petoria.repository;


import com.petoria.model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Page<Pet> findAllByOrderBySubmissionTimeDesc(Pageable pageable);
    Page<Pet> findByTypeIgnoreCase(String type, Pageable pageable);
}
