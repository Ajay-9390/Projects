package com.anr.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.anr.entities.Weather;

@Service
public class WeatherService {

	@Value("${weather.api.key}")
	private String apiKey;

	public Weather getWeather(String city) {
		String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> response = restTemplate.getForObject(url, Map.class);

		Weather weather = new Weather();
		weather.setCity((String) response.get("name"));
		Map<String, Object> main = (Map<String, Object>) response.get("main");
		weather.setTemperature(((Number) main.get("temp")).doubleValue());
		List<Map<String, Object>> weatherList = (List<Map<String, Object>>) response.get("weather");
		weather.setCondition((String) weatherList.get(0).get("description"));

		return weather;
	}
}
