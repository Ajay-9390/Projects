package com.anr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anr.entities.Weather;
import com.anr.service.WeatherService;

@Controller
public class WeatherController {

	@Autowired
	private WeatherService weatherService;

	@GetMapping("/weather")
	public String getWeather(@RequestParam String city, Model model) {
		Weather weather = weatherService.getWeather(city);
		model.addAttribute("weather", weather);
		return "weather";
	}
}
