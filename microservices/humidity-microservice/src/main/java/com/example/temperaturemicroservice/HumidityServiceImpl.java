package com.example.springboot;
import com.example.springboot.HumidityEntity;
import com.example.springboot.HumidityRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// Annotation
@Service
// Class
public class HumidityServiceImpl implements HumidityService{
    // Dependency
    @Autowired
    private HumidityRepository HumidityRepository;
    // Method
    @Override
    public List<HumidityEntity> getAllHumidities() {
        return (List<HumidityEntity>) HumidityRepository.findAll();
    }
    // Method
    @Override
    public HumidityEntity getHumidityById(Integer id) {
        return HumidityRepository.findById(id).get();
    }
    // Method
    @Override
    public HumidityEntity createHumidity(HumidityEntity Humidity) {
        return HumidityRepository.save(Humidity);
    }
}