package com.petoria.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostRequestDto {
    private String content;
    private List<MultipartFile> images;
    private List<MultipartFile> videos;
}
