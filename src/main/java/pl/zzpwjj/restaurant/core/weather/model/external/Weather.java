package pl.zzpwjj.restaurant.core.weather.model.external;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Weather {
    private Long id;
    private String name;
    private Integer cod;
    private Long dt;
    private String base;
    private Integer visibility;
    private Coord coord;
    private List<Weath> weather;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private Sys sys;
}
