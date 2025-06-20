package com.petoria.dto;

import com.petoria.model.ServiceType;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceProviderDto {

    private String name;
    private String logoUrl;
    private String location;
    private String phone;
    private String websiteUrl;
    private String description;
    private Set<ServiceType> types;

}
