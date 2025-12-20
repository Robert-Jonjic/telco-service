package com.telco.api.dto;

public class AddressResponse {

    private Integer id;
    private String streetNo;
    private String street;
    private String city;
    private String post;
    private Integer postNo;

    public AddressResponse() {}

    public AddressResponse(Integer id, String streetNo, String street, String city, String post, Integer postNo) {
        this.id = id;
        this.streetNo = streetNo;
        this.street = street;
        this.city = city;
        this.post = post;
        this.postNo = postNo;
    }

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
}
