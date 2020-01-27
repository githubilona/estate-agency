package com.example.estateagency.repositories;

import com.example.estateagency.models.OfferType;
import com.example.estateagency.models.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    //nazwa metody jest jednocześnie zapytaniem
    Page<Property> findByNameContaining(String phrase, Pageable pageable);

    //nad klasą Property znajduje się definicja zapytania (@NamedQuery) powiązana z tą metodą
    Page<Property> findAllPropertiesUsingNamedQuery(String phrase, Pageable pageable);

    @Query("SELECT p FROM Property p WHERE " +
            "(" +
            ":phrase is null OR :phrase = '' OR "+
            "upper(p.name) LIKE upper(:phrase) OR " +
            "upper(p.description) LIKE upper(:phrase) OR " +
            "upper(p.propertyType.name) LIKE upper(:phrase)" +
            ") " +
            "AND " +
            "(:min is null OR :min <= p.price) " +
            "AND (:max is null OR :max >= p.price)" +
            "AND "+
            "(" +
            ":offerType is null OR :offerType = '' OR "+
            "upper(p.offerType.name) LIKE upper(:offerType)" +
            ") " +
            "AND "+
            "(" +
            ":propertyType is null OR :propertyType = '' OR "+
            "upper(p.propertyType.name) LIKE upper(:propertyType)" +
            ") " +
            "AND "+
            "(" +
            ":province is null OR :province = '' OR "+
            "upper(p.address.province.name) LIKE upper(:province)" +
            ") " +
            "AND "+
            "(" +
            ":city is null OR :city = '' OR "+
            "upper(p.address.city) LIKE upper(:city)" +
            ") "
           )
    Page<Property> findAllPropertiesUsingFilter(@Param("phrase") String p, @Param("min") Float priceMin, @Param("max") Float priceMax,
                                                @Param("offerType") String offerType, @Param("propertyType") String propertyType,
                                                @Param("province") String province, @Param("city") String city, Pageable pageable);

    @Query("SELECT p FROM Property p WHERE p.user.id = :id")
    Page<Property> findAllByUserId(@Param("id") Long id, Pageable pageable);

}
