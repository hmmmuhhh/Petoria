package com.petoria.repository;

import com.petoria.model.ServiceProvider;
import com.petoria.model.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

    Page<ServiceProvider> findByTypesContaining(ServiceType type, Pageable pageable);
}
