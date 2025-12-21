package com.telco.api.web;

import com.telco.api.domain.Address;
import com.telco.api.domain.ServiceFootprint;
import com.telco.api.dto.FootprintAddressResponse;
import com.telco.api.dto.ServiceFootprintResponse;
import com.telco.api.repository.AddressRepository;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Validated
@RestController
@RequestMapping(
        value = "/api/footprint",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
)
public class FootprintController {

    private final AddressRepository addressRepository;

    public FootprintController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    // 1) Query by address ID
    @GetMapping("/addresses/{addressId}")
    public FootprintAddressResponse byAddressId(@PathVariable @Min(1) Integer addressId) {
        Address a = addressRepository.findWithServicesById(addressId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Address not found: " + addressId));

        return toFootprint(a);
    }

    // 2) Query by other address fields
    @GetMapping("/addresses/search")
    public List<FootprintAddressResponse> search(
            @RequestParam(required = false) String streetNo,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String post,
            @RequestParam(required = false) @Min(1) Integer postNo
    ) {
        // Require at least one filter
        if (isBlank(streetNo) && isBlank(street) && isBlank(city) && isBlank(post) && postNo == null) {
            throw new ResponseStatusException(BAD_REQUEST, "At least one query parameter must be provided.");
        }

        // Normalize blanks to null (so query behaves correctly)
        streetNo = blankToNull(streetNo);
        street = blankToNull(street);
        city = blankToNull(city);
        post = blankToNull(post);

        return addressRepository.searchWithServices(streetNo, street, city, post, postNo).stream()
                .map(this::toFootprint)
                .toList();
    }

    private FootprintAddressResponse toFootprint(Address a) {
        FootprintAddressResponse r = new FootprintAddressResponse();
        r.setId(a.getId());
        r.setStreetNo(a.getStreetNo());
        r.setStreet(a.getStreet());
        r.setCity(a.getCity());
        r.setPost(a.getPost());
        r.setPostNo(a.getPostNo());

        List<ServiceFootprintResponse> services = a.getServices().stream()
                .map(this::toServiceResponse)
                .toList();

        r.setServices(services);
        return r;
    }

    private ServiceFootprintResponse toServiceResponse(ServiceFootprint sf) {
        return new ServiceFootprintResponse(sf.getId(), sf.getType(), sf.isAvailable(), sf.getComment());
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private String blankToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
