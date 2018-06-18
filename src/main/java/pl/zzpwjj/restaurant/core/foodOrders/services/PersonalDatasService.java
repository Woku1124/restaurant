package pl.zzpwjj.restaurant.core.foodOrders.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddPersonalDataInput;
import pl.zzpwjj.restaurant.core.foodOrders.model.dto.PersonalDataDto;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.PersonalData;
import pl.zzpwjj.restaurant.core.foodOrders.repositories.PersonalDatasRepository;

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

    public PersonalData addPersonalData(final AddPersonalDataInput addPersonalDataInput) {
        //create object
        PersonalData personalData = new PersonalData();
        personalData.setName(addPersonalDataInput.getName());
        personalData.setSurname(addPersonalDataInput.getSurname());

        //save
        return personalDatasRepository.save(personalData);
    }

    public void deletePersonalData(final Long id) throws ItemNotFoundException {
        if (!personalDatasRepository.existsById(id)) {
            throw new ItemNotFoundException("Personal data with id = " + id + " does not exist");
        }

        personalDatasRepository.deleteById(id);
    }

    public PersonalData editPersonalData(final PersonalDataDto personalDataDto) throws ItemNotFoundException {
        if (!personalDatasRepository.existsById(personalDataDto.getId())) {
            throw new ItemNotFoundException("Personal data with id = " + personalDataDto.getId() + " does not exist");
        }

        PersonalData personalData = new PersonalData();
        personalData.setId(personalDataDto.getId());
        personalData.setName(personalDataDto.getName());
        personalData.setSurname(personalDataDto.getSurname());

        return personalDatasRepository.save(personalData);
    }
}