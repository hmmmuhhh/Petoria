package com.petoria.controller;

import com.petoria.dto.CommentDto;
import com.petoria.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog/{postId}/comments")
@RequiredArgsConstructor
public class BlogCommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentDto>> getBlogComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsForBlog(postId));
    }

    @PostMapping
    public ResponseEntity<Void> addBlogComment(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails user,
            @RequestBody CommentDto dto
    ) {
        commentService.addCommentToBlog(postId, user.getUsername(), dto);
        return ResponseEntity.ok().build();
    }
}
