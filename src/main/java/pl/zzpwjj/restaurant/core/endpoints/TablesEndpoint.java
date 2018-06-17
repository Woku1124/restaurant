package pl.zzpwjj.restaurant.core.endpoints;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.zzpwjj.restaurant.common.exceptions.DataSourceException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.converters.TableConverter;
import pl.zzpwjj.restaurant.core.model.dto.TableDto;
import pl.zzpwjj.restaurant.core.model.inputs.AddTableInput;
import pl.zzpwjj.restaurant.core.services.TablesService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class TablesEndpoint {
    private TablesService tablesService;
    private TableConverter tableConverter;

    @Autowired
    public TablesEndpoint(final TablesService tablesService, final TableConverter tableConverter)
    {
        this.tableConverter=tableConverter;
        this.tablesService=tablesService;
    }

    @ApiOperation(value = "Returns all tables")
    @GetMapping("/getTables")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<TableDto>> getTables()
    {
        return new ResponseEntity<>(tableConverter.convertTables(tablesService.getAllTables()), HttpStatus.OK);
    }

    @ApiOperation(value = "Returns table by id")
    @GetMapping("/getTable")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<TableDto> getTable(@RequestParam("id") @NotNull final Long id)
    {
        try{
            return new ResponseEntity<>(tableConverter.convertTable(tablesService.getTable(id)),HttpStatus.OK);
        }
        catch (ItemNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @ApiOperation(value = "Adds a table")
    @PostMapping("/addTable")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<String> addTable(@RequestBody @Valid final AddTableInput addTableInput)
    {
        tablesService.addTable(addTableInput);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Removes table")
    @DeleteMapping("/deleteTable")
    public ResponseEntity<String> deleteTable(@RequestParam("id") @NotNull final Long id) {
        try {
            tablesService.deleteTable(id);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Table not found", HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

