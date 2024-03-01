package com.example.springboot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.example.springboot.TemperatureServiceImpl;
import com.example.springboot.TemperatureEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Application implements CommandLineRunner {

  @Autowired 
  private TemperatureServiceImpl temperatureService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

  @Override
  public void run(String... args) throws Exception {
    ArrayList<String> readings = readCSV("readings.csv");
    System.out.println(readings);
    
    for(int i = 0; i < readings.size(); i++){
      TemperatureEntity temperature = new TemperatureEntity(readings.get(i));
      temperatureService.createTemperature(temperature);
    }
  }

  static ArrayList<String> readCSV(String path){
    ArrayList<String> readings = new ArrayList<String>();
    try {
      BufferedReader br = new BufferedReader(new FileReader(path));
      String line = br.readLine();
      line = br.readLine();
      while (line != null) {
        String[] values = line.split(",");
        String temperature = values[0];
        readings.add(temperature);
        line = br.readLine();
      }
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return readings;
  }

}

