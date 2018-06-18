package pl.zzpwjj.restaurant.core.finances.converters;

import org.springframework.stereotype.Component;
import pl.zzpwjj.restaurant.core.finances.model.dto.OutgoingDto;
import pl.zzpwjj.restaurant.core.finances.model.entities.Outgoing;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OutgoingsConverter {

    public OutgoingDto convertOutgoing(final Outgoing outgoing) {
        OutgoingDto outgoingDto = new OutgoingDto();
        outgoingDto.setId(outgoing.getId());
        outgoingDto.setTypeOfOutgoing(outgoing.getTypeOfOutgoing());
        outgoingDto.setValueOfOutgoing(outgoing.getValueOfOutgoing());
        outgoingDto.setPesel(outgoing.getPesel());
        outgoingDto.setDateOfOutgoing(outgoing.getDateOfOutgoing());

        return outgoingDto;
    }

    public List<OutgoingDto> convertOutgoings(final List<Outgoing> outgoings) {
        return outgoings.stream().map(this::convertOutgoing).collect(Collectors.toList());
    }
}