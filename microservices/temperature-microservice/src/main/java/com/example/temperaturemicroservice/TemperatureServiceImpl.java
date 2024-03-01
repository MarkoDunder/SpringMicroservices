package com.example.springboot;
import com.example.springboot.TemperatureEntity;
import com.example.springboot.TemperatureRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// Annotation
@Service
// Class
public class TemperatureServiceImpl implements TemperatureService{
    // Dependency
    @Autowired
    private TemperatureRepository temperatureRepository;
    // Method
    @Override
    public List<TemperatureEntity> getAllTemperatures() {
        return (List<TemperatureEntity>) temperatureRepository.findAll();
    }
    // Method
    @Override
    public TemperatureEntity getTemperatureById(Integer id) {
        return temperatureRepository.findById(id).get();
    }
    // Method
    @Override
    public TemperatureEntity createTemperature(TemperatureEntity temperature) {
        return temperatureRepository.save(temperature);
    }
}