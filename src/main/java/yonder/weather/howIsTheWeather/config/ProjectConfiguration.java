package yonder.weather.howIsTheWeather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ProjectConfiguration {
    @Bean
    WebClient webClient() {
        return WebClient.builder().baseUrl("https://998d8129-2264-4a98-a92e-ba8bde4a4d1c.mock.pstmn.io/")
                .build();
    }
}
