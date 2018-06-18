package pl.zzpwjj.restaurant.core.weather.endpoints;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zzpwjj.restaurant.core.weather.converters.WeatherConverter;
import pl.zzpwjj.restaurant.core.weather.model.dto.WeatherDto;
import pl.zzpwjj.restaurant.core.weather.services.WeatherService;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestMapping("/weather")
@Api(value = "Weather endpoint")
@RestController
public class WeatherEndpoint {

    private WeatherService weatherService;
    private WeatherConverter weatherConverter;

    @Autowired
    public WeatherEndpoint(final WeatherService weatherService, final WeatherConverter weatherConverter) {
        this.weatherService = weatherService;
        this.weatherConverter = weatherConverter;
    }

    @ApiOperation(value = "Returns current weather")
    @GetMapping("/getWeather")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<WeatherDto> getWeather() {
        WeatherDto weatherDto = weatherConverter.convertWeather(weatherService.getWeather());
        return new ResponseEntity<>(weatherDto, HttpStatus.OK);
    }
}