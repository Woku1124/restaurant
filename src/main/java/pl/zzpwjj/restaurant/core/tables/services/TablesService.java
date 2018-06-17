package pl.zzpwjj.restaurant.core.tables.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.reservations.model.entities.Reservation;
import pl.zzpwjj.restaurant.core.tables.model.entities.Table;
import pl.zzpwjj.restaurant.core.tables.model.inputs.AddTableInput;
import pl.zzpwjj.restaurant.core.tables.repositories.TablesRepository;
import pl.zzpwjj.restaurant.core.reservations.services.ReservationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TablesService {
    private TablesRepository tablesRepository;
    private ReservationService reservationService;

    @Autowired
    public TablesService(final TablesRepository tablesRepository, @Lazy final ReservationService reservationService)
    {
        this.tablesRepository=tablesRepository;
        this.reservationService=reservationService;
    }

    public List<Table> getAllTables()
    {
        return tablesRepository.findAll();
    }

    public Table getTable(final Long id) throws ItemNotFoundException {
        return tablesRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public List<Table> getFreeTables()
    {
        return tablesRepository.findAll().stream().filter(e -> e.getReservationId()==null).collect(Collectors.toList());
    }

    public Table reserveTable(Reservation reservation, Table table){
        table.setReservationId(reservation);
        return tablesRepository.save(table);
    }

    public Table addTable(AddTableInput addTableInput)
    {
        Table table = new Table();
        table.setReservationId(addTableInput.getReservationId());

        return tablesRepository.save(table);
    }

    public void deleteTable(final Long id) throws ItemNotFoundException {
        try {
            Table table = tablesRepository.findById(id).get();
            reservationService.deleteReservation(table.getReservationId().getId());
            tablesRepository.deleteById(id);

        } catch (ItemNotFoundException e) {
            throw new ItemNotFoundException("Table with id = " + id + " does not exist", e);
        }

    }
}
