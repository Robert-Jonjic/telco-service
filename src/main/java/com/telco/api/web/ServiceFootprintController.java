package com.telco.api.web;

import com.telco.api.domain.Address;
import com.telco.api.domain.ServiceFootprint;
import com.telco.api.dto.ServiceFootprintRequest;
import com.telco.api.dto.ServiceFootprintResponse;
import com.telco.api.repository.AddressRepository;
import com.telco.api.repository.ServiceFootprintRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/addresses/{addressId}/services",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
)
public class ServiceFootprintController {

    private final AddressRepository addressRepository;
    private final ServiceFootprintRepository serviceRepository;

    public ServiceFootprintController(AddressRepository addressRepository,
                                      ServiceFootprintRepository serviceRepository) {
        this.addressRepository = addressRepository;
        this.serviceRepository = serviceRepository;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceFootprintResponse create(@PathVariable Integer addressId,
                                           @Valid @RequestBody ServiceFootprintRequest req) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found: " + addressId));

        // Enforce uniqueness per address
        if (serviceRepository.existsByAddress_IdAndType(addressId, req.getType())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Service type already exists for address " + addressId + ": " + req.getType());
        }

        ServiceFootprint sf = new ServiceFootprint();
        sf.setType(req.getType());
        sf.setAvailable(Boolean.TRUE.equals(req.getAvailable()));
        sf.setComment(trimToNull(req.getComment()));
        sf.setAddress(address);

        ServiceFootprint saved = serviceRepository.save(sf);
        return toResponse(saved);
    }

    @GetMapping
    public List<ServiceFootprintResponse> list(@PathVariable Integer addressId) {
        ensureAddressExists(addressId);
        return serviceRepository.findByAddress_Id(addressId).stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{serviceId}")
    public ServiceFootprintResponse get(@PathVariable Integer addressId, @PathVariable Long serviceId) {
        ServiceFootprint sf = serviceRepository.findByIdAndAddress_Id(serviceId, addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Service not found for address " + addressId + ": " + serviceId));
        return toResponse(sf);
    }

    @PutMapping(
            value = "/{serviceId}",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ServiceFootprintResponse update(@PathVariable Integer addressId,
                                           @PathVariable Long serviceId,
                                           @Valid @RequestBody ServiceFootprintRequest req) {

        ServiceFootprint sf = serviceRepository.findByIdAndAddress_Id(serviceId, addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Service not found for address " + addressId + ": " + serviceId));

        // If type is changing, enforce uniqueness constraint before saving
        if (req.getType() != sf.getType()
                && serviceRepository.existsByAddress_IdAndType(addressId, req.getType())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Service type already exists for address " + addressId + ": " + req.getType());
        }

        sf.setType(req.getType());
        sf.setAvailable(Boolean.TRUE.equals(req.getAvailable()));
        sf.setComment(trimToNull(req.getComment()));

        ServiceFootprint saved = serviceRepository.save(sf);
        return toResponse(saved);
    }

    @DeleteMapping("/{serviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer addressId, @PathVariable Long serviceId) {
        ServiceFootprint sf = serviceRepository.findByIdAndAddress_Id(serviceId, addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Service not found for address " + addressId + ": " + serviceId));
        serviceRepository.delete(sf);
    }

    private void ensureAddressExists(Integer addressId) {
        if (!addressRepository.existsById(addressId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found: " + addressId);
        }
    }

    private ServiceFootprintResponse toResponse(ServiceFootprint sf) {
        return new ServiceFootprintResponse(sf.getId(), sf.getType(), sf.isAvailable(), sf.getComment());
    }

    private String trimToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
