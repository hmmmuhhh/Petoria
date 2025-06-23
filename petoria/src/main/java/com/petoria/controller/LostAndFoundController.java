package com.petoria.controller;

import com.petoria.dto.LostAndFoundNoticeDto;
import com.petoria.model.User;
import com.petoria.security.CustomUserDetails;
import com.petoria.service.LostAndFoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class LostAndFoundController {

    private final LostAndFoundService noticeService;

    @GetMapping
    public Page<LostAndFoundNoticeDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String type
    ) {
        return noticeService.getNotices(page, type);
    }

    @PostMapping
    public LostAndFoundNoticeDto create(@RequestBody LostAndFoundNoticeDto dto) {
        User user = getAuthenticatedUser();
        return noticeService.create(dto, user);
    }

    @GetMapping("/{id}")
    public LostAndFoundNoticeDto getById(@PathVariable Long id) {
        return noticeService.getById(id);
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = new User();
        user.setId(userDetails.getId());
        return user;
    }
}

