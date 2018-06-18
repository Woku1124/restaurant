package pl.zzpwjj.restaurant.core.finances.endpoints;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.finances.converters.IncomesConverter;
import pl.zzpwjj.restaurant.core.finances.converters.OutgoingsConverter;
import pl.zzpwjj.restaurant.core.finances.model.dto.IncomeDto;
import pl.zzpwjj.restaurant.core.finances.model.dto.OutgoingDto;
import pl.zzpwjj.restaurant.core.finances.services.IncomesService;
import pl.zzpwjj.restaurant.core.finances.services.OutgoingsService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestMapping("/finances")
@Api(value = "Finances endpoint")
@RestController
public class FinancesEndpoint {

    private IncomesService incomesService;
    private IncomesConverter incomesConverter;
    private OutgoingsService outgoingsService;
    private OutgoingsConverter outgoingsConverter;


    @Autowired
    public FinancesEndpoint(final IncomesService incomesService, final IncomesConverter incomesConverter,
                             final OutgoingsService outgoingsService, OutgoingsConverter outgoingsConverter) {
        this.incomesService = incomesService;
        this.incomesConverter = incomesConverter;
        this.outgoingsService = outgoingsService;
        this.outgoingsConverter = outgoingsConverter;
    }

    @ApiOperation(value = "Returns all incomes")
    @GetMapping("/getIncomes")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<IncomeDto>> getIncomes() {
        List<IncomeDto> incomes = incomesConverter.convertIncomes(incomesService.getIncomes());
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns an income")
    @GetMapping("/getIncome")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<IncomeDto> getIncome(@RequestParam("id") @NotNull final Long id) {
        IncomeDto income;
        try {
            income = incomesConverter.convertIncome(incomesService.getIncome(id));
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(income, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds an income")
    @PostMapping("/addIncome")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> addIncome(@RequestBody @Valid final Double valueOfIncome) {
        incomesService.addIncome(valueOfIncome);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @ApiOperation(value = "Returns all outgoings")
    @GetMapping("/getOutgoings")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<OutgoingDto>> getOutgoings() {
        List<OutgoingDto> outgoings = outgoingsConverter.convertOutgoings(outgoingsService.getOutgoings());
        return new ResponseEntity<>(outgoings, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns an outgoing")
    @GetMapping("/getIncome")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<OutgoingDto> getOutgoing(@RequestParam("id") @NotNull final Long id) {
        OutgoingDto outgoing;
        try {
            outgoing = outgoingsConverter.convertOutgoing(outgoingsService.getOutgoing(id));
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(outgoing, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds an outgoings for all employees")
    @PostMapping("/paySalaries")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> paySalaries() throws ItemNotFoundException {
        outgoingsService.paySalaries();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}