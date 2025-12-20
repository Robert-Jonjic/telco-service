package com.telco.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddressRequest {

    @NotBlank
    @Size(max = 32)
    private String streetNo;

    @NotBlank
    @Size(max = 255)
    private String street;

    @NotBlank
    @Size(max = 128)
    private String city;

    @NotBlank
    @Size(max = 128)
    private String post;

    @NotNull
    @Min(1)
    private Integer postNo;

    public AddressRequest() {}

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
}
