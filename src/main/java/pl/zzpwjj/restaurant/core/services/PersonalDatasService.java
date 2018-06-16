package pl.zzpwjj.restaurant.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.model.inputs.AddPersonalDataInput;
import pl.zzpwjj.restaurant.core.model.dto.PersonalDataDto;
import pl.zzpwjj.restaurant.core.model.entities.PersonalData;
import pl.zzpwjj.restaurant.core.repositories.PersonalDatasRepository;

import java.util.List;

@Service
public class PersonalDatasService {

    private PersonalDatasRepository personalDatasRepository;

    @Autowired
    public PersonalDatasService(final PersonalDatasRepository personalDatasRepository) {
        this.personalDatasRepository = personalDatasRepository;
    }

    public List<PersonalData> getPersonalDatas() {
        return personalDatasRepository.findAll();
    }

    public PersonalData getPersonalData(final Long id) throws ItemNotFoundException {
        return personalDatasRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public void addPersonalData(final AddPersonalDataInput addPersonalDataInput) {
        PersonalData personalData = new PersonalData();
        personalData.setName(addPersonalDataInput.getName());
        personalData.setSurname(addPersonalDataInput.getSurname());

        personalDatasRepository.save(personalData);
    }

    public void deletePersonalData(final Long id) throws ItemNotFoundException {
        try {
            personalDatasRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("Personal data with id = " + id + " does not exist", e);
        }
    }

    public void editPersonalData(final PersonalDataDto personalDataDto) throws InvalidParametersException {
        if (!personalDatasRepository.existsById(personalDataDto.getId())) {
            throw new InvalidParametersException("Personal data with id = " + personalDataDto.getId() + " does not exist");
        }

        PersonalData personalData = new PersonalData();
        personalData.setId(personalDataDto.getId());
        personalData.setName(personalDataDto.getName());
        personalData.setSurname(personalDataDto.getSurname());

        personalDatasRepository.save(personalData);
    }
}