package com.petoria.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogPostResponseDto {
    private Long id;
    private String content;
    private List<String> imagePaths;
    private List<String> videoPaths;
    private String username;
    private String profilePicture;
    private LocalDateTime createdAt;
}
