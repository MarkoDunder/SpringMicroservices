package com.example.springboot;
//import entity
import com.example.springboot.TemperatureEntity;
//import repository
import com.example.springboot.TemperatureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface TemperatureService {
    List<TemperatureEntity> getAllTemperatures();
    TemperatureEntity getTemperatureById(Integer id);
    TemperatureEntity createTemperature(TemperatureEntity temperature);
}
