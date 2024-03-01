package com.example.springboot;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.springboot.TemperatureEntity;


@Repository

public interface TemperatureRepository extends CrudRepository<TemperatureEntity, Integer> {

}