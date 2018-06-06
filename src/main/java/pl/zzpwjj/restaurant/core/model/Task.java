package pl.zzpwjj.restaurant.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Task {
    private String title;
    private String description;
}