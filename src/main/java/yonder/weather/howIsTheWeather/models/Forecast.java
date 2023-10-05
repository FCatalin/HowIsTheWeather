package yonder.weather.howIsTheWeather.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Forecast {
    private String day;
    private Double temperature;
    private Double wind;
}
