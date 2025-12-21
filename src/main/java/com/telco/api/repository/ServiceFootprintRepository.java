package com.telco.api.repository;

import com.telco.api.domain.ServiceFootprint;
import com.telco.api.domain.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceFootprintRepository extends JpaRepository<ServiceFootprint, Long> {

    List<ServiceFootprint> findByAddress_Id(Integer addressId);

    Optional<ServiceFootprint> findByIdAndAddress_Id(Long id, Integer addressId);

    boolean existsByAddress_IdAndType(Integer addressId, ServiceType type);
}
