package com.telco.api.web;

import com.telco.api.domain.Address;
import com.telco.api.dto.AddressRequest;
import com.telco.api.dto.AddressResponse;
import com.telco.api.repository.AddressRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(
        value = "/api/addresses",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
)
public class AddressController {

    private final AddressRepository addressRepository;

    public AddressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponse create(@Valid @RequestBody AddressRequest req) {
        Address a = new Address();
        // ID is assigned by us to avoid conflicting with seed IDs.
        // Simple approach: pick next ID = max+1.
        int nextId = addressRepository.findAll().stream()
                .map(Address::getId)
                .max(Comparator.naturalOrder())
                .orElse(0) + 1;

        a.setId(nextId);
        apply(a, req);

        Address saved = addressRepository.save(a);
        return toResponse(saved);
    }

    @GetMapping("/{id}")
    public AddressResponse get(@PathVariable Integer id) {
        Address a = addressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found: " + id));
        return toResponse(a);
    }

    @GetMapping
    public List<AddressResponse> list() {
        return addressRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @PutMapping(
            value = "/{id}",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public AddressResponse update(@PathVariable Integer id, @Valid @RequestBody AddressRequest req) {
        Address a = addressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found: " + id));

        apply(a, req);

        Address saved = addressRepository.save(a);
        return toResponse(saved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        if (!addressRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found: " + id);
        }
        addressRepository.deleteById(id);
    }

    private void apply(Address a, AddressRequest req) {
        a.setStreetNo(req.getStreetNo());
        a.setStreet(req.getStreet());
        a.setCity(req.getCity());
        a.setPost(req.getPost());
        a.setPostNo(req.getPostNo());
    }

    private AddressResponse toResponse(Address a) {
        return new AddressResponse(
                a.getId(),
                a.getStreetNo(),
                a.getStreet(),
                a.getCity(),
                a.getPost(),
                a.getPostNo()
        );
    }
}
