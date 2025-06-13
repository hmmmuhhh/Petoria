package com.example.demo.repository;


import com.example.demo.model.ListedPet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListedPetRepository extends JpaRepository<ListedPet, Long> {
    Page<ListedPet> findAllByOrderBySubmissionTimeDesc(Pageable pageable);
    Page<ListedPet> findByTypeIgnoreCase(String type, Pageable pageable);
}
