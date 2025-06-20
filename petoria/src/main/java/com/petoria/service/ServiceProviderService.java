package com.petoria.service;

import com.petoria.model.ServiceProvider;
import com.petoria.model.ServiceType;
import com.petoria.repository.ServiceProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ServiceProviderService {

    private final ServiceProviderRepository repository;

    public Page<ServiceProvider> getAllProviders(String type, Pageable pageable) {
        if (type != null && ServiceType.isValid(type)) {
            return repository.findByTypesContaining(ServiceType.fromString(type), pageable);
        }
        return repository.findAll(pageable);
    }

    public ServiceProvider addProvider(ServiceProvider provider) {
        return repository.save(provider);
    }
}
