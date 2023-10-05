package yonder.weather.howIsTheWeather.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private String name;
    private String temperature;
    private String wind;
}
