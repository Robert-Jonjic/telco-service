package com.telco.api.dto;

import com.telco.api.domain.ServiceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ServiceFootprintRequest {

    @NotNull
    private ServiceType type;

    @NotNull
    private Boolean available;

    @Size(max = 1024)
    private String comment;

    public ServiceFootprintRequest() {}

    public ServiceType getType() { return type; }
    public void setType(ServiceType type) { this.type = type; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
