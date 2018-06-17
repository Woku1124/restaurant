package pl.zzpwjj.restaurant.core.taskexample.model.inputs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddTaskInput {
    @NotNull
    private String title;
    @NotNull
    private String description;
}