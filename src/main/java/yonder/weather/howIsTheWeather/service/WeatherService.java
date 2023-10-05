package yonder.weather.howIsTheWeather.service;

import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import yonder.weather.howIsTheWeather.models.ApiResponse;
import yonder.weather.howIsTheWeather.models.Forecast;
import yonder.weather.howIsTheWeather.models.Result;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {
    private final WebClient webClient;

    public Mono<Result> fetchDeterminedWeather(String cityName) {
        return fetchWeather(cityName)
                .map(apiResponse -> {
                    Result result = new Result();
                    result.setName(cityName);
                    determineAvgTemperatureAndWind(result, apiResponse.getForecast());

                    return result;
                });
    }

    private void determineAvgTemperatureAndWind(Result result, List<Forecast> forecastList) {
        if (Objects.nonNull(forecastList)) {
            result.setTemperature(String.valueOf(forecastList.stream()
                    .mapToDouble(Forecast::getTemperature)
                    .average()
                    .getAsDouble()));
            result.setWind(String.valueOf(forecastList.stream()
                    .mapToDouble(Forecast::getWind)
                    .average()
                    .getAsDouble()));
        } else {
            result.setWind("");
            result.setTemperature("");
        }

    }

    private Mono<ApiResponse> fetchWeather(String cityName) {
        return webClient.get()
                .uri("/{cityName}", cityName)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .onErrorResume(WebClientResponseException.class,
                        ex -> Mono.just(new ApiResponse()));
    }
}
