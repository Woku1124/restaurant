package pl.zzpwjj.restaurant.core.weather.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.core.weather.clients.WeatherApiClient;
import pl.zzpwjj.restaurant.core.weather.model.external.Weather;

@Service
public class WeatherService {

    private WeatherApiClient weatherApiClient;

    @Autowired
    public WeatherService(final WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;
    }

    public Weather getWeather() {
        return weatherApiClient.call();
    }
}