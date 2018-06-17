package pl.zzpwjj.restaurant.core.model.inputs;

        import lombok.AllArgsConstructor;
        import lombok.Getter;
        import lombok.NoArgsConstructor;
        import lombok.Setter;
        import pl.zzpwjj.restaurant.core.model.entities.Dish;

        import javax.validation.constraints.Max;
        import javax.validation.constraints.Min;
        import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddRatingInput {

    @NotNull
    private Dish dish_id;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer mark;

    @NotNull
    private String comment;
}