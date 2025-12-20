package com.telco.api.domain;

import jakarta.persistence.*;

@Entity
@Table(
        name = "service_footprints",
        uniqueConstraints = @UniqueConstraint(name = "uk_address_service_type", columnNames = {"address_id", "type"})
)
public class ServiceFootprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private ServiceType type;

    @Column(nullable = false)
    private boolean available;

    @Column(length = 1024)
    private String comment;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    public ServiceFootprint() {}

    // Getters/Setters
    public Long getId() { return id; }

    public ServiceType getType() { return type; }
    public void setType(ServiceType type) { this.type = type; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
}
