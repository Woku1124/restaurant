package pl.zzpwjj.restaurant.core.finances.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OutgoingDto {

    @NotNull
    private Long id;

    @NotNull
    private String typeOfOutgoing;

    @NotNull
    private Double valueOfOutgoing;

    private String pesel;

    @NotNull
    private LocalDateTime dateOfOutgoing;
}