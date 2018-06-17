package pl.zzpwjj.restaurant.core.taskexample.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @NotNull
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String description;
}