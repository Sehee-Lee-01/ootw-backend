package com.backendoori.ootw.weather.controller;

import com.backendoori.ootw.weather.dto.WeatherResponse;
import com.backendoori.ootw.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    public ResponseEntity<WeatherResponse> readCurrentWeather(@RequestParam(defaultValue = "55") int nx,
                                                              @RequestParam(defaultValue = "127") int ny) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(weatherService.getCurrentWeather(nx, ny));
    }

}
