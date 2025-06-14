package com.petoria.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ListedPetDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String photoUrl;
    private String type;
    private Long userId;
}
