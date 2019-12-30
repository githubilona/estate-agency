package com.example.estateagency.repositories;

import com.example.estateagency.models.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
            "AND (:max is null OR :max >= p.price)")
    Page<Property> findAllPropertiesUsingFilter(@Param("phrase") String p, @Param("min") Float priceMin, @Param("max") Float priceMax, Pageable pageable);


}
