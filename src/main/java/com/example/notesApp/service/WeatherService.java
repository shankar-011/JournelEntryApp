package com.example.notesApp.service;

import com.example.notesApp.constants.Placeholders;
import com.example.notesApp.api.response.WeatherResponse;
import com.example.notesApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;
    @Autowired
    AppCache appCache;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if(weatherResponse != null){
            return weatherResponse;
        }else{
            String finalApi = appCache.getAppCache().get(Placeholders.API_NAME).replace(Placeholders.API_KEY, apiKey).replace(Placeholders.CITY, city);
            ResponseEntity<WeatherResponse> response =  restTemplate.exchange(finalApi, HttpMethod.GET,null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if (body!=null){
                redisService.set("weather_of_"+city,body,300l);
            }
            return body;

        }

    }
}
