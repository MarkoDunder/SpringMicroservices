package com.example.springboot;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.springboot.HumidityEntity;


@Repository

public interface HumidityRepository extends CrudRepository<HumidityEntity, Integer> {

}