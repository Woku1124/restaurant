package pl.zzpwjj.restaurant.core.dishes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.zzpwjj.restaurant.core.dishes.model.entities.DishType;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishDto {
    @NotNull
    private Long id;

    @NotNull
    private DishType dishType;

    @NotNull
    private Double price;

    @NotNull
    private String name;

    @NotNull
    private String description;

}
