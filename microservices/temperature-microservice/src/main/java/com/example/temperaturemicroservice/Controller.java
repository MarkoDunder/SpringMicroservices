package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboot.TemperatureServiceImpl;
import com.example.springboot.TemperatureEntity;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import java.util.List;
import java.util.ArrayList;


@RestController
public class Controller {

  @Autowired 
  private TemperatureServiceImpl temperatureService;

	@GetMapping("/")
	public String index() {
    System.out.println("Recieved request");
    long currentTimestamp = System.currentTimeMillis();
    System.out.println("Current timestamp: " + currentTimestamp);
    long differenceInMinutes = currentTimestamp / 60000;
    int index = ((int)differenceInMinutes % 100) + 1;
    System.out.println("Index: " + index);
    try{
      TemperatureEntity temperature = temperatureService.getTemperatureById(index);
      // return JSON with name, unit and value
      return "{\"name\":\"Temperature\",\"unit\":\"C\",\"value\":" + temperature.getTemperature() + "}";
    }
    catch(Exception e){
      System.out.println("Error: " + e);
      return "{\"name\":\"Temperature\",\"unit\":\"C\",\"value\":0}";
	  }
}
  
  }


@RestController
class ServiceInstanceRestController {

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("/service-instances/{applicationName}")
  // return getHomePageUrl() for each instance
  public List<String> serviceInstancesByApplicationName(
      @PathVariable String applicationName) {
    List<String> urls = new ArrayList<String>();
    List<ServiceInstance> serviceInstances = discoveryClient.getInstances(applicationName);
    for (ServiceInstance serviceInstance : serviceInstances) {
      urls.add(serviceInstance.getUri().toString());
    }
    return urls;
  }
}







