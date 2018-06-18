package pl.zzpwjj.restaurant.core.weather.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import pl.zzpwjj.restaurant.core.weather.model.external.Weather;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:externalSystems.properties")
@Controller
public class WeatherApiClient {

    @Value("${weatherApiURL}")
    private String URL;
    @Value("${weatherApiKEY}")
    private String KEY;
    @Value("${weatherApiCITY}")
    private String CITY;

    public Weather call() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(URL + "?q="+ CITY + "&APPID=" + KEY, Weather.class);
    }
}
