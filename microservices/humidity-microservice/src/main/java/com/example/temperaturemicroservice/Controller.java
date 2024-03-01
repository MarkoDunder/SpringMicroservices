package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboot.HumidityServiceImpl;
import com.example.springboot.HumidityEntity;
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
  private HumidityServiceImpl humidityService;

	@GetMapping("/")
	public String index() {
    System.out.println("Recieved request");
    long currentTimestamp = System.currentTimeMillis();
    long differenceInMinutes = currentTimestamp / 60000;
    int index = ((int)differenceInMinutes % 100) + 1;
    HumidityEntity humidity = humidityService.getHumidityById(index);
    // return JSON with name, unit and value
    return "{\"name\":\"Humidity\",\"unit\":\"%\",\"value\":" + humidity.getHumidity() + "}";
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
