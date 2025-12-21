package com.telco.api.repository;

import com.telco.api.domain.Address;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @EntityGraph(attributePaths = "services")
    Optional<Address> findWithServicesById(Integer id);

    @EntityGraph(attributePaths = "services")
    @Query("""
        select a from Address a
        where (:streetNo is null or a.streetNo = :streetNo)
          and (:postNo is null or a.postNo = :postNo)
          and (:street is null or lower(a.street) like concat('%', lower(:street), '%'))
          and (:city is null or lower(a.city) like concat('%', lower(:city), '%'))
          and (:post is null or lower(a.post) like concat('%', lower(:post), '%'))
        order by a.id
        """)
    List<Address> searchWithServices(
            @Param("streetNo") String streetNo,
            @Param("street") String street,
            @Param("city") String city,
            @Param("post") String post,
            @Param("postNo") Integer postNo
    );
}
