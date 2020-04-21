package com.example.restservice.repository;

import com.example.restservice.GreetingEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

// JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default.
//      Therefore, database queries may be performed during view rendering.
//          Explicitly configure spring.jpa.open-in-view to disable this warning
@RepositoryRestResource(collectionResourceRel = "greetings", path = "greetings")
public interface GreetingRepository extends PagingAndSortingRepository<GreetingEntity, Long> {

    List<GreetingEntity> findByGreeting(@Param("greeting") String greeting);

}
