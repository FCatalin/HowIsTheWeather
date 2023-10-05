package yonder.weather.howIsTheWeather.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private String temperature;
    private String wind;
    private String description;
    private List<Forecast> forecast;
}
