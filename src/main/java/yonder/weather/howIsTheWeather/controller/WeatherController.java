package yonder.weather.howIsTheWeather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import yonder.weather.howIsTheWeather.models.ResponseDTO;
import yonder.weather.howIsTheWeather.models.Result;
import yonder.weather.howIsTheWeather.service.CsvWriterService;
import yonder.weather.howIsTheWeather.service.WeatherService;
import yonder.weather.howIsTheWeather.util.Constants;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;
    private final CsvWriterService csvWriterService;

    @GetMapping(value = "/api/weather")
    public Mono<ResponseEntity<ResponseDTO>> getWeatherForCities(@RequestParam(required = true, name = "city") List<String> cityList) {
        List<String> filteredCities = cityList.stream()
                .filter(cityName -> Constants.validCityList.contains(cityName))
                .collect(Collectors.toList());


        return Flux.fromIterable(filteredCities)
                .flatMap(weatherService::fetchDeterminedWeather)
                .sort(Comparator.comparing(Result::getName))
                .collectList()
                .map(resultList -> {
                    try {
                        csvWriterService.writeDataToCSV(resultList);
                    } catch (IOException ignored) {
                    }
                    return ResponseEntity.ok(new ResponseDTO(resultList));
                });


    }
}
