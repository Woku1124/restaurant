package pl.zzpwjj.restaurant.core.weather.model.external;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Weath {
    private Integer id;
    private String main;
    private String description;
    private String icon;
}
