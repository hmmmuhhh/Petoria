package com.petoria.controller;

import com.petoria.dto.NoticeDto;
import com.petoria.dto.NoticeResponseDto;
import com.petoria.model.User;
import com.petoria.security.CustomUserDetails;
import com.petoria.service.LostAndFoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

//@RestController
//@RequestMapping("/api/notices")
//@RequiredArgsConstructor
//public class LostAndFoundController {
//
//    private final LostAndFoundService noticeService;
//
//    @GetMapping
//    public Page<NoticeDto> getAll(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(required = false) String type
//    ) {
//        return noticeService.getAllNotices(page, type);
//    }
//
//    @PostMapping
//    public NoticeDto create(@RequestBody NoticeDto dto) {
//        User user = getAuthenticatedUser();
//        return noticeService.create(dto, user);
//    }
//
//    @GetMapping("/{id}")
//    public NoticeDto getById(@PathVariable Long id) {
//        return noticeService.getById(id);
//    }
//
//    private User getAuthenticatedUser() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
//        User user = new User();
//        user.setId(userDetails.getId());
//        return user;
//    }
//
//    @PutMapping("/{id}/mark-found")
//    public ResponseEntity<Void> markAsFound(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) throws AccessDeniedException {
//        noticeService.toggleFoundStatus(id, user.getUsername());
//        return ResponseEntity.ok().build();
//    }
//
//}
//

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class LostAndFoundController {

    private final LostAndFoundService noticeService;

    @PostMapping
    public ResponseEntity<Void> createNotice(@AuthenticationPrincipal UserDetails user,
                                             @ModelAttribute NoticeDto dto) throws IOException {
        noticeService.createNotice(user.getUsername(), dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<NoticeResponseDto>> getNotices(@AuthenticationPrincipal UserDetails user,
                                                              @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(noticeService.getAllNotices(user.getUsername(), page));
    }

    @PutMapping("/{id}/mark-found")
    public ResponseEntity<Void> markAsFound(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) throws AccessDeniedException {
        noticeService.toggleFoundStatus(id, user.getUsername());
        return ResponseEntity.ok().build();
    }
}

