package pl.zzpwjj.restaurant.core.weather.model.external;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sys {
    private Integer type;
    private Integer id;
    private Double message;
    private String country;
    private Long sunrise;
    private Long sunset;
}