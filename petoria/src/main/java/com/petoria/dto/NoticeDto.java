package com.petoria.dto;

import com.petoria.model.NoticeType;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto {
    private String title;
    private String description;
    private String location;
    private NoticeType type;
    private MultipartFile[] photos;
}

