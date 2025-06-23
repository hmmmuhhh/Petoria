package com.petoria.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String text;
    private String imageUrl;
    private LocalDateTime submissionTime;
    private String authorUsername;
    private String authorProfilePicUrl;
}
