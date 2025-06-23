package com.petoria.dto;

import com.petoria.model.ServiceType;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceProviderDto {
    private Long id;
    private String name;
    private String logoUrl;
    private String location;
    private String phone;
    private String websiteUrl;
    private String description;
    private List<String> types;

}
