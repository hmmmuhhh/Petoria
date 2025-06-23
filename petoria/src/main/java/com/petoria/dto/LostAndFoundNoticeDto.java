package com.petoria.dto;

import com.petoria.model.NoticeType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LostAndFoundNoticeDto {
    private Long id;
    private String title;
    private String description;
    private String photoUrl;
    private String location;
    private NoticeType type;
    private LocalDateTime submissionTime;
    private String authorUsername;
    private String authorProfilePicUrl;
    private boolean isFound;
}

