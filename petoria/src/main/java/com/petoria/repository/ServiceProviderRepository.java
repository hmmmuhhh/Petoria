package com.petoria.repository;

import com.petoria.model.ServiceProvider;
import com.petoria.model.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

    @Query("SELECT DISTINCT sp FROM ServiceProvider sp JOIN sp.types t WHERE t = :type")
    Page<ServiceProvider> findByType(@Param("type") ServiceType type, Pageable pageable);
}
