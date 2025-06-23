package com.petoria.service;

import com.petoria.dto.LostAndFoundNoticeDto;
import com.petoria.model.LostAndFoundNotice;
import com.petoria.model.NoticeType;
import com.petoria.model.User;
import com.petoria.repository.LostAndFoundNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LostAndFoundService {

    private final LostAndFoundNoticeRepository noticeRepo;

    public Page<LostAndFoundNoticeDto> getNotices(int page, String typeStr) {
        Pageable pageable = PageRequest.of(page, 9, Sort.by("submissionTime").descending());

        if (typeStr != null && NoticeType.isValid(typeStr)) {
            NoticeType type = NoticeType.valueOf(typeStr.toUpperCase());
            return noticeRepo.findAllByType(type, pageable).map(this::toDto);
        }

        return noticeRepo.findAll(pageable).map(this::toDto);
    }

    public LostAndFoundNoticeDto getById(Long id) {
        return noticeRepo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notice not found"));
    }

    public LostAndFoundNoticeDto create(LostAndFoundNoticeDto dto, User user) {
        LostAndFoundNotice notice = LostAndFoundNotice.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .photoUrl(dto.getPhotoUrl())
                .location(dto.getLocation())
                .type(dto.getType())
                .submissionTime(LocalDateTime.now())
                .creator(user)
                .build();

        return toDto(noticeRepo.save(notice));
    }

    private LostAndFoundNoticeDto toDto(LostAndFoundNotice n) {
        return LostAndFoundNoticeDto.builder()
                .id(n.getId())
                .title(n.getTitle())
                .description(n.getDescription())
                .photoUrl(n.getPhotoUrl())
                .location(n.getLocation())
                .type(n.getType())
                .submissionTime(n.getSubmissionTime())
                .authorUsername(n.getCreator().getUsername())
                .authorProfilePicUrl(n.getCreator().getProfilePicUrl())
                .build();
    }
}

