package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import java.util.List;
import java.util.ArrayList;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


@RestController
@RefreshScope
public class Controller {


  @Value("${unit.value}")
  private String temperatureUnit;
  @Autowired
	private DiscoveryClient discoveryClient;


	@GetMapping("/")
	public String index() {
    System.out.println("Recieved request");
    System.out.println("Temperature unit is: " + temperatureUnit);
    // send GET request to temperature microservice at port 8080
    // send GET request to humidity microservice at port 8888
    // return JSON with name, unit and value
    if(discoveryClient.getInstances("temperature-microservice").size() == 0){
      return "Temperature microservice is not available";
    }
    if(discoveryClient.getInstances("humidity-microservice").size() == 0){
      return "Humidity microservice is not available";
    }
    
    String temperatureMicroserviceUri = discoveryClient.getInstances("temperature-microservice").get(0).getUri().toString();

    try{
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(temperatureMicroserviceUri))
        .build();
  
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      String responseBody = response.body();

      //parse temperature to jsonObject

      JSONObject jsonObject = new JSONObject(responseBody);
      String temperatureString = jsonObject.getString("value");
      Integer temperature = Integer.parseInt(temperatureString);

      if(temperatureUnit.equals("K")){
        temperature = temperature + 273;
      }
      
      String humidityMicroserviceUri = discoveryClient.getInstances("humidity-microservice").get(0).getUri().toString();
      request = HttpRequest.newBuilder()
        .uri(URI.create(humidityMicroserviceUri))
        .build();
  
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
      responseBody = response.body();

     
      if(temperatureUnit.equals("K")){
        return "[{\"name\": \"Temperature\", \"value\": " + temperature + ".15, \"unit\": \""+temperatureUnit+"\" },"+responseBody + "]";
      }

      return "[{\"name\": \"Temperature\", \"value\": " + temperature + ", \"unit\": \""+temperatureUnit+"\" },"+responseBody + "]";
     
    }
    catch(IOException e){
      System.out.println("IOException");
      return "IOException";
    }
    catch(InterruptedException e){
      System.out.println("InterruptedException");
      return "InterruptedException";
    }
    catch(Exception e){
      System.out.println("Exception");
      return "Exception";
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

