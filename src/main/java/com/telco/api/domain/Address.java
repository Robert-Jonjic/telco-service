package com.telco.api.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    private Integer id; // comes from seed

    @Column(nullable = false, length = 32)
    private String streetNo;

    @Column(nullable = false, length = 255)
    private String street;

    @Column(nullable = false, length = 128)
    private String city;

    @Column(nullable = false, length = 128)
    private String post;

    @Column(nullable = false)
    private Integer postNo;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceFootprint> services = new ArrayList<>();

    public Address() {}

    // Convenience helper
    public void addService(ServiceFootprint service) {
        services.add(service);
        service.setAddress(this);
    }

    public void removeService(ServiceFootprint service) {
        services.remove(service);
        service.setAddress(null);
    }

    // Getters/Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getStreetNo() { return streetNo; }
    public void setStreetNo(String streetNo) { this.streetNo = streetNo; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPost() { return post; }
    public void setPost(String post) { this.post = post; }

    public Integer getPostNo() { return postNo; }
    public void setPostNo(Integer postNo) { this.postNo = postNo; }

    public List<ServiceFootprint> getServices() { return services; }
    public void setServices(List<ServiceFootprint> services) { this.services = services; }
}
