package com.petoria.dto;

import com.petoria.model.PetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetResponseDto {
    private Long id;
    private String name;
    private PetType type;
    private BigDecimal price;
    private String description;
    private List<String> photoPaths;
    private boolean sold;
    private String posterUsername;
    private String posterProfilePicUrl;
    private boolean isOwner;
}
