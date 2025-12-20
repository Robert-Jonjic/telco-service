package com.telco.api.seed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

public class SeedModels {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SeedRecord {
        public SeedAddress address;
        public List<SeedService> services;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SeedAddress {
        public Integer id;
        public String streetNo;
        public String street;
        public String city;
        public String post;
        public Integer postNo;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SeedService {
        public String service;  // "Internet" / "Telefon" / "Televizija"
        public Boolean value;
        public String comment;
    }
}
