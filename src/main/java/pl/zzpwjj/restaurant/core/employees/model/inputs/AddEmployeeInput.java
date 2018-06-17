package pl.zzpwjj.restaurant.core.employees.model.inputs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddEmployeeInput {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String pesel;
    @NotNull
    private String position;
    @NotNull
    private double salary;
}