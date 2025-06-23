package com.petoria.controller;

import com.petoria.dto.ServiceProviderDto;
import com.petoria.model.ServiceProvider;
import com.petoria.model.ServiceType;
import com.petoria.model.User;
import com.petoria.security.CustomUserDetails;
import com.petoria.service.ServiceProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceProviderController {

    private final ServiceProviderService service;
    User user = new User();

    @GetMapping
    public Page<ServiceProviderDto> getAll(
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page
    ) {
        return service.getAllProviders(type, PageRequest.of(page, 9));
    }

    @PostMapping
    public ServiceProvider add(@RequestBody ServiceProviderDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        User user = new User();
        user.setId(userDetails.getId());

        ServiceProvider provider = ServiceProvider.builder()
                .name(dto.getName())
                .logoUrl(dto.getLogoUrl())
                .location(dto.getLocation())
                .websiteUrl(dto.getWebsiteUrl())
                .description(dto.getDescription())
                .types(dto.getTypes().stream()
                        .map(type -> ServiceType.valueOf(type.toUpperCase()))
                        .collect(Collectors.toSet()))
                .phone(dto.getPhone())
                .creator(user)
                .build();

        return service.addProvider(provider);
    }

}
