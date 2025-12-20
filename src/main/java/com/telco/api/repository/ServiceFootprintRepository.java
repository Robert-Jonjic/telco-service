package com.telco.api.repository;

import com.telco.api.domain.ServiceFootprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceFootprintRepository extends JpaRepository<ServiceFootprint, Long> {
}
