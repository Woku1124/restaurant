package pl.zzpwjj.restaurant.core.weather.model.external;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Main {
    private Double temp;
    private Integer pressure;
    private Integer humidity;
    private Double temp_min;
    private Double temp_max;
}
