package com.petoria.repository;

import com.petoria.model.LostAndFoundNotice;
import com.petoria.model.NoticeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LostAndFoundNoticeRepository extends JpaRepository<LostAndFoundNotice, Long> {
    Page<LostAndFoundNotice> findAllByType(NoticeType type, Pageable pageable);
    Optional<LostAndFoundNotice> findById(Long id);
}

