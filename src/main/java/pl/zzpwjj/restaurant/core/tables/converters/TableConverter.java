package pl.zzpwjj.restaurant.core.tables.converters;


import org.springframework.stereotype.Component;
import pl.zzpwjj.restaurant.core.tables.model.dto.TableDto;
import pl.zzpwjj.restaurant.core.tables.model.entities.Table;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TableConverter {

    public TableDto convertTable(Table table)
    {
        TableDto tableDto = new TableDto();
        tableDto.setId(table.getId());
        tableDto.setReservationId(table.getReservationId());
        return tableDto;
    }

    public List<TableDto> convertTables(final List<Table> tables) {
        return tables.stream().map(this::convertTable).collect(Collectors.toList());
    }
}
