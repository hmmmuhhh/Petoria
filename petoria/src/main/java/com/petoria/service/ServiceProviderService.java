package com.petoria.service;

import com.petoria.dto.ServiceProviderDto;
import com.petoria.model.ServiceProvider;
import com.petoria.model.ServiceType;
import com.petoria.repository.ServiceProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ServiceProviderService {

    private final ServiceProviderRepository repository;

//    public Page<ServiceProvider> getAllProviders(String type, Pageable pageable) {
//        if (type != null && ServiceType.isValid(type)) {
//            return repository.findByTypesContaining(ServiceType.fromString(type), pageable);
//        }
//        return repository.findAll(pageable);
//    }

    public Page<ServiceProviderDto> getAllProviders(String typeStr, Pageable pageable) {
        if (typeStr != null && !typeStr.isBlank() && ServiceType.isValid(typeStr)) {
            ServiceType type = ServiceType.valueOf(typeStr.toUpperCase());
            return repository.findByType(type, pageable).map(this::toDto);
        }

        System.out.println("Requested type: " + typeStr);
        System.out.println("Providers found: " + repository.findAll().size());

        return repository.findAll(pageable).map(this::toDto);
    }

    private ServiceProviderDto toDto(ServiceProvider provider) {
        return ServiceProviderDto.builder()
                .id(provider.getId())
                .name(provider.getName())
                .description(provider.getDescription())
                .location(provider.getLocation())
                .phone(provider.getPhone())
                .logoUrl(provider.getLogoUrl())
                .websiteUrl(provider.getWebsiteUrl())
                .types(provider.getTypes().stream()
                        .map(ServiceType::name)
                        .toList()
                )
                .build();
    }

    public ServiceProvider addProvider(ServiceProvider provider) {
        return repository.save(provider);
    }
}
