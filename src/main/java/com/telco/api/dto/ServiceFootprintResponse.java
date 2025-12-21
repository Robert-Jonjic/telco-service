package com.telco.api.dto;

import com.telco.api.domain.ServiceType;

public class ServiceFootprintResponse {

    private Long id;
    private ServiceType type;
    private boolean available;
    private String comment;

    public ServiceFootprintResponse() {}

    public ServiceFootprintResponse(Long id, ServiceType type, boolean available, String comment) {
        this.id = id;
        this.type = type;
        this.available = available;
        this.comment = comment;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ServiceType getType() { return type; }
    public void setType(ServiceType type) { this.type = type; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
