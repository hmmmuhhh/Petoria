package com.petoria.controller;

import com.petoria.dto.CommentDto;
import com.petoria.model.User;
import com.petoria.repository.UserRepository;
import com.petoria.service.CommentService;
import com.petoria.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/notices/{noticeId}/comments")
@RequiredArgsConstructor
public class LostAndFoundCommentController {

    private final CommentService commentService;
    private final TokenUtils tokenUtils;
    private final UserRepository userRepo;

    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long noticeId) {
        List<CommentDto> comments = commentService.getCommentsForNotice(noticeId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<CommentDto> addComment(
            @PathVariable Long noticeId,
            @RequestBody CommentDto dto,
            @RequestHeader("Authorization") String authHeader
    ) {
        User user = tokenUtils.extractUser(authHeader, userRepo);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token or user not found.");
        }

        CommentDto saved = commentService.addComment(noticeId, dto, user);
        return ResponseEntity.ok(saved);
    }

}



