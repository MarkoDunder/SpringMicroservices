package com.example.springboot;
//import entity
import com.example.springboot.HumidityEntity;
//import repository
import com.example.springboot.HumidityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface HumidityService {
    List<HumidityEntity> getAllHumidities();
    HumidityEntity getHumidityById(Integer id);
    HumidityEntity createHumidity(HumidityEntity Humidity);
}
