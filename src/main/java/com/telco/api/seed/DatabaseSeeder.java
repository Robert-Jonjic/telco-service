package com.telco.api.seed;

import com.telco.api.domain.Address;
import com.telco.api.domain.ServiceFootprint;
import com.telco.api.domain.ServiceType;
import com.telco.api.repository.AddressRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.io.InputStream;
import java.util.List;

@Component
@ConditionalOnProperty(name = "telco.seeding.enabled", havingValue = "true", matchIfMissing = true)
public class DatabaseSeeder implements ApplicationRunner {

    private final AddressRepository addressRepository;
    private final JsonMapper jsonMapper;

    public DatabaseSeeder(AddressRepository addressRepository, JsonMapper jsonMapper) {
        this.addressRepository = addressRepository;
        this.jsonMapper = jsonMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (addressRepository.count() > 0) {
            return; // already seeded
        }

        ClassPathResource resource = new ClassPathResource("data/seed.json");

        // If the seed file isn't present, don't fail app startup (and don't fail tests accidentally)
        if (!resource.exists()) {
            System.out.println("Seed file not found at classpath:data/seed.json (skipping seeding)");
            return;
        }

        try (InputStream is = resource.getInputStream()) {
            List<SeedModels.SeedRecord> records =
                    jsonMapper.readValue(is, new TypeReference<List<SeedModels.SeedRecord>>() {});

            for (SeedModels.SeedRecord record : records) {
                if (record == null || record.address == null) continue;

                Address a = new Address();
                a.setId(record.address.id);
                a.setStreetNo(record.address.streetNo);
                a.setStreet(record.address.street);
                a.setCity(record.address.city);
                a.setPost(record.address.post);
                a.setPostNo(record.address.postNo);

                if (record.services != null) {
                    for (SeedModels.SeedService svc : record.services) {
                        if (svc == null) continue;

                        ServiceFootprint sf = new ServiceFootprint();
                        sf.setType(ServiceType.fromSeedValue(svc.service));
                        sf.setAvailable(Boolean.TRUE.equals(svc.value));
                        sf.setComment(svc.comment == null ? null : svc.comment.trim());

                        a.addService(sf);
                    }
                }

                addressRepository.save(a);
            }
        }

        System.out.println("Seeded database from classpath:data/seed.json");
    }
}
