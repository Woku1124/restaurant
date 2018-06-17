package pl.zzpwjj.restaurant.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.DataSourceException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.model.entities.Reservation;
import pl.zzpwjj.restaurant.core.model.entities.Table;
import pl.zzpwjj.restaurant.core.model.inputs.AddTableInput;
import pl.zzpwjj.restaurant.core.repositories.TablesRepository;

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

    public void reserveTable(Reservation reservation, Table table){
        table.setReservationId(reservation);
        tablesRepository.save(table);
    }

    public void addTable(AddTableInput addTableInput)
    {
        Table table = new Table();
        table.setReservationId(addTableInput.getReservationId());

        tablesRepository.save(table);
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
