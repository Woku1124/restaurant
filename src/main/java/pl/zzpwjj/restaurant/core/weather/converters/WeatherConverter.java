package pl.zzpwjj.restaurant.core.weather.converters;

import org.springframework.stereotype.Component;
import pl.zzpwjj.restaurant.core.weather.model.dto.WeatherDto;
import pl.zzpwjj.restaurant.core.weather.model.external.Weather;

@Component
public class WeatherConverter {

    public WeatherDto convertWeather(final Weather weather) {
        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setPlace(weather.getName());
        weatherDto.setDescription(weather.getWeather().get(0).getDescription());
        weatherDto.setHumidity(weather.getMain().getHumidity().toString());
        weatherDto.setPressure(weather.getMain().getPressure().toString());
        weatherDto.setTemperature(weather.getMain().getTemp().toString());
        weatherDto.setVisibility(weather.getVisibility().toString());
        weatherDto.setWindSpeed(weather.getWind().getSpeed().toString());

        return weatherDto;
    }
}
