package com.example.restservice.repository;

import java.util.List;

import com.example.restservice.PersonEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends PagingAndSortingRepository<PersonEntity, Long> {

	List<PersonEntity> findByLastName(@Param("name") String name);

}
