package pl.zzpwjj.restaurant.core.employees.model.inputs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddEmployeeInput {
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