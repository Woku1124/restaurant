package pl.zzpwjj.restaurant.core.converters;


import pl.zzpwjj.restaurant.core.model.dto.TableDto;
import pl.zzpwjj.restaurant.core.model.entities.Table;

import java.util.List;
import java.util.stream.Collectors;

public class TableConverter {

    public TableDto convertTable(Table table)
    {
        TableDto tableDto = new TableDto();
        tableDto.setId(table.getId());
        tableDto.setReservationId(table.getReservation());
        return tableDto;
    }

    public List<TableDto> convertTables(final List<Table> tables) {
        return tables.stream().map(this::convertTable).collect(Collectors.toList());
    }
}
