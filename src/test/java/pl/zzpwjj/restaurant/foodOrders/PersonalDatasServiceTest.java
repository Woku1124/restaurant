package pl.zzpwjj.restaurant.foodOrders;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.zzpwjj.restaurant.common.exceptions.ItemNotFoundException;
import pl.zzpwjj.restaurant.core.foodOrders.model.dto.PersonalDataDto;
import pl.zzpwjj.restaurant.core.foodOrders.model.entities.PersonalData;
import pl.zzpwjj.restaurant.core.foodOrders.model.input.AddPersonalDataInput;
import pl.zzpwjj.restaurant.core.foodOrders.repositories.PersonalDatasRepository;
import pl.zzpwjj.restaurant.core.foodOrders.services.PersonalDatasService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PersonalDatasServiceTest {
    @InjectMocks
    private PersonalDatasService personalDatasService;

    @Mock
    private PersonalDatasRepository personalDatasRepository;

    @Test
    public void shouldCreatePersonalData() {
        // given
        AddPersonalDataInput personalData = new AddPersonalDataInput();
        personalData.setName("Kinga");
        personalData.setSurname("Mirkiewicz");

        // when
        Mockito.when(personalDatasRepository.save(Mockito.any(PersonalData.class))).then(AdditionalAnswers.returnsFirstArg());
        PersonalData createdPersonalData = personalDatasService.addPersonalData(personalData);

        // then
        Assert.assertEquals(personalData.getName(), createdPersonalData.getName());
        Assert.assertEquals(personalData.getSurname(), createdPersonalData.getSurname());

    }

    @Test
    public void shouldDeletePersonalData() throws ItemNotFoundException {
        // given
        Long id = 1L;

        // when
        Mockito.when(personalDatasRepository.existsById(id)).thenReturn(true);
        Mockito.doNothing().when(personalDatasRepository).deleteById(id);
        personalDatasService.deletePersonalData(id);

        // then
        Mockito.verify(personalDatasRepository, Mockito.times(1)).deleteById(id);
    }

    @Test(expected = ItemNotFoundException.class)
    public void shouldNotDeletePersonalDataButThrowItemNotFoundException() throws ItemNotFoundException {
        // given
        Long id = 1L;

        // when
        Mockito.when(personalDatasRepository.existsById(id)).thenReturn(false);
        personalDatasService.deletePersonalData(id);

        // then throw an exception
    }

    @Test
    public void shouldUpdatePersonalData() throws ItemNotFoundException {
        // given
        PersonalDataDto personalData = new PersonalDataDto();
        personalData.setId(1l);
        personalData.setName("Kinga");
        personalData.setSurname("Mirkiewicz");

        // when
        Mockito.when(personalDatasRepository.existsById(personalData.getId())).thenReturn(true);
        Mockito.when(personalDatasRepository.save(Mockito.any(PersonalData.class))).then(AdditionalAnswers.returnsFirstArg());
        PersonalData updatedPersonalData = personalDatasService.editPersonalData(personalData);

        // then
        Assert.assertEquals(personalData.getName(), updatedPersonalData.getName());
        Assert.assertEquals(personalData.getSurname(), updatedPersonalData.getSurname());

    }

    @Test(expected = ItemNotFoundException.class)
    public void shouldNotUpdatePersonalDataButThrowItemNotFoundException() throws ItemNotFoundException {
        // given
        PersonalDataDto personalData = new PersonalDataDto();
        personalData.setId(1l);
        personalData.setName("Kinga");
        personalData.setSurname("Mirkiewicz");

        // when
        Mockito.when(personalDatasRepository.existsById(personalData.getId())).thenReturn(false);
        PersonalData updatedPersonalData = personalDatasService.editPersonalData(personalData);

        // then throw an exception
    }
}