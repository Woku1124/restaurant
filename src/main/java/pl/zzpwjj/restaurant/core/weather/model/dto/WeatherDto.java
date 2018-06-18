package pl.zzpwjj.restaurant.core.weather.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherDto {
    private String place;
    private String temperature;
    private String pressure;
    private String humidity;
    private String visibility;
    private String windSpeed;
    private String description;
}
