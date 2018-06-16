package pl.zzpwjj.restaurant.core.endpoints;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.converters.AddressesConverter;
import pl.zzpwjj.restaurant.core.model.dto.AddressDto;
import pl.zzpwjj.restaurant.core.model.inputs.AddAddressInput;
import pl.zzpwjj.restaurant.core.services.AddressesService;

@RequestMapping("/addresses")
@Api(value = "Addresses endpoint")
@RestController
public class AddressesEndpoint {

    private AddressesService addressesService;
    private AddressesConverter addressesConverter;

    @Autowired
    public AddressesEndpoint(final AddressesService addressesService, final AddressesConverter addressesConverter) {
        this.addressesService = addressesService;
        this.addressesConverter = addressesConverter;
    }

    @ApiOperation(value = "Returns all addresses")
    @GetMapping("/getAddresses")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<AddressDto>> getAddresses() {
        List<AddressDto> addressDtos = addressesConverter.convertAddresses(addressesService.getAddresses());
        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns address")
    @GetMapping("/getAddress")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<AddressDto> getAddress(@RequestParam("id") @NotNull final Long id) {
        AddressDto addressDto;
        try {
            addressDto = addressesConverter.convertAddress(addressesService.getAddress(id));
        }
        catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds address")
    @PostMapping("/addAddress")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> addAddress(@RequestBody @Valid final AddAddressInput addAddressInput) {
        addressesService.addAddress(addAddressInput);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Removes address")
    @DeleteMapping("/deleteAddress")
    public ResponseEntity<Void> deleteAddress(@RequestParam("id") @NotNull final Long id) {
        try {
            addressesService.deleteAddress(id);
        }
        catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Edits address")
    @PostMapping("/editAddress")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> editAddress(@RequestBody @Valid final AddressDto addressDto) {
        try {
            addressesService.editAddress(addressDto);
        }
        catch (InvalidParametersException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}