package com.telco.api.dto;

import java.util.ArrayList;
import java.util.List;

public class FootprintAddressResponse {

    private Integer id;
    private String streetNo;
    private String street;
    private String city;
    private String post;
    private Integer postNo;

    private List<ServiceFootprintResponse> services = new ArrayList<>();

    public FootprintAddressResponse() {}

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

    public List<ServiceFootprintResponse> getServices() { return services; }
    public void setServices(List<ServiceFootprintResponse> services) { this.services = services; }
}
