package com.petoria.dto;

import com.petoria.model.NoticeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class NoticeResponseDto {
    private Long id;
    private String title;
    private String description;
    private String location;
    private List<String> photoPaths;
    private boolean found;
    private NoticeType type;
    private String posterUsername;
    private String posterProfilePicUrl;
    private boolean isOwner;

}

