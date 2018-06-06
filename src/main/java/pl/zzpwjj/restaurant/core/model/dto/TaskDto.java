package pl.zzpwjj.restaurant.core.model.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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