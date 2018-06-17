package pl.zzpwjj.restaurant.core.employees.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity(name = "employees")
public class Employee {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    // TODO AT: pattern
    @NotNull
    private String pesel;

    @NotNull
    private String position;

    // TODO AT: pattern
    @NotNull
    private double salary;
}