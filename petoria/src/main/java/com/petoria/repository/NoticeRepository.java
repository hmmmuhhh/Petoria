package com.petoria.repository;

import com.petoria.model.Notice;
import com.petoria.model.NoticeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Page<Notice> findAllByType(NoticeType type, Pageable pageable);
    Optional<Notice> findById(Long id);
}

