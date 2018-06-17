package pl.zzpwjj.restaurant.core.dishes.model.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddDishInput {

    @NotNull
    private AddDishTypeInput dishTypeInput;

    @NotNull
    private Double price;

    @NotNull
    private String name;

    @NotNull
    private String description;
}
