package pl.zzpwjj.restaurant.core.employees.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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

    @Pattern(regexp = "^\\d{11}$", message = "Pesel consists of 11 digits")
    @NotNull
    private String pesel;

    @NotNull
    private String position;

    @Range(min = 0, message = "Salary must be positive number")
    @NotNull
    private double salary;
}