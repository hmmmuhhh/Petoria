package com.petoria.dto;

import com.petoria.model.PetType;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PetDto {

    private String name;
    private String description;
    private BigDecimal price;
    private PetType type;
    private List<MultipartFile> photos;

}
