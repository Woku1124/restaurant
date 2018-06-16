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
import pl.zzpwjj.restaurant.core.converters.PersonalDatasConverter;
import pl.zzpwjj.restaurant.core.model.dto.PersonalDataDto;
import pl.zzpwjj.restaurant.core.model.inputs.AddPersonalDataInput;
import pl.zzpwjj.restaurant.core.services.PersonalDatasService;

@RequestMapping("/personalDatas")
@Api(value = "Personal data endpoint")
@RestController
public class PersonalDatasEndpoint {

    private PersonalDatasService personalDatasService;
    private PersonalDatasConverter personalDatasConverter;

    @Autowired
    public PersonalDatasEndpoint(final PersonalDatasService personalDatasService, final PersonalDatasConverter personalDatasConverter) {
        this.personalDatasService = personalDatasService;
        this.personalDatasConverter = personalDatasConverter;
    }

    @ApiOperation(value = "Returns all personal datas")
    @GetMapping("/getPersonalDatas")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<PersonalDataDto>> getPersonalDatas() {
        List<PersonalDataDto> personalDataDtos = personalDatasConverter.convertPersonalDatas(personalDatasService.getPersonalDatas());
        return new ResponseEntity<>(personalDataDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns personal data")
    @GetMapping("/getPersonalData")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<PersonalDataDto> getPersonalData(@RequestParam("id") @NotNull final Long id) {
        PersonalDataDto personalDataDto;
        try {
            personalDataDto = personalDatasConverter.convertPersonalData(personalDatasService.getPersonalData(id));
        }
        catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(personalDataDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds personal data")
    @PostMapping("/addPersonalData")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> addPersonalData(@RequestBody @Valid final AddPersonalDataInput addPersonalDataInput) {
        personalDatasService.addPersonalData(addPersonalDataInput);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Removes personal dara")
    @DeleteMapping("/deletePersonalData")
    public ResponseEntity<Void> deletePersonalData(@RequestParam("id") @NotNull final Long id) {
        try {
            personalDatasService.deletePersonalData(id);
        }
        catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Edits personal data")
    @PostMapping("/editPersonalData")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> editPersonalData(@RequestBody @Valid final PersonalDataDto personalDataDto) {
        try {
            personalDatasService.editPersonalData(personalDataDto);
        }
        catch (InvalidParametersException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}