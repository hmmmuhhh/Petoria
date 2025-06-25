package com.petoria.controller;

import com.petoria.dto.BlogPostRequestDto;
import com.petoria.dto.BlogPostResponseDto;
import com.petoria.service.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogPostService blogPostService;

    @PostMapping
    public ResponseEntity<?> createPost(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("content") String content,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @RequestParam(value = "videos", required = false) List<MultipartFile> videos
    ) throws IOException {
        BlogPostRequestDto dto = new BlogPostRequestDto(content, images, videos);
        blogPostService.createPost(userDetails.getUsername(), dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<BlogPostResponseDto>> getAllPosts() {
        return ResponseEntity.ok(blogPostService.getAllPosts());
    }
}
