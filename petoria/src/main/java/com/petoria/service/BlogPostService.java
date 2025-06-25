package com.petoria.service;

import com.petoria.dto.BlogPostRequestDto;
import com.petoria.dto.BlogPostResponseDto;
import com.petoria.model.BlogPost;
import com.petoria.model.User;
import com.petoria.repository.BlogPostRepository;
import com.petoria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final UserRepository userRepository;

    public void createPost(String username, BlogPostRequestDto dto) throws IOException {
        User user = userRepository.findByEmailOrUsername(username, username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> imagePaths = saveFiles(dto.getImages(), "image");
        List<String> videoPaths = saveFiles(dto.getVideos(), "video");

        BlogPost post = BlogPost.builder()
                .content(dto.getContent())
                .creator(user)
                .createdAt(LocalDateTime.now())
                .imagePaths(imagePaths)
                .videoPaths(videoPaths)
                .build();

        blogPostRepository.save(post);
    }

    public List<BlogPostResponseDto> getAllPosts() {
        List<BlogPost> posts = blogPostRepository.findAllByOrderByCreatedAtDesc();
        List<BlogPostResponseDto> response = new ArrayList<>();

        for (BlogPost post : posts) {
            response.add(BlogPostResponseDto.builder()
                    .id(post.getId())
                    .content(post.getContent())
                    .imagePaths(post.getImagePaths())
                    .videoPaths(post.getVideoPaths())
                    .username(post.getCreator().getUsername())
                    .profilePicture(post.getCreator().getProfilePicUrl())
                    .createdAt(post.getCreatedAt())
                    .build());
        }

        return response;
    }

    private List<String> saveFiles(List<MultipartFile> files, String type) throws IOException {
        List<String> paths = new ArrayList<>();
        if (files == null) return paths;

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String uploadDir = "uploads/";
            File targetFolder = new File(uploadDir);
            if (!targetFolder.exists()) {
                boolean created = targetFolder.mkdirs();
                if (!created) {
                    throw new IOException("Failed to create upload directory: " + uploadDir);
                }
            }
            Path filePath = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            paths.add("/media/uploads/" + fileName);
        }
        return paths;
    }

}
