package pl.zzpwjj.restaurant.core.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "tasks")
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;
}