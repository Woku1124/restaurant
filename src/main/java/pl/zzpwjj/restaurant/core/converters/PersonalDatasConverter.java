package pl.zzpwjj.restaurant.core.converters;

import pl.zzpwjj.restaurant.core.model.dto.PersonalDataDto;
import pl.zzpwjj.restaurant.core.model.entities.PersonalData;

import java.util.List;
import java.util.stream.Collectors;

public class PersonalDatasConverter {
    public PersonalDataDto convertPersonalData(final PersonalData personalData) {
        PersonalDataDto personalDataDto = new PersonalDataDto();
        personalDataDto.setId(personalData.getId());
        personalDataDto.setName(personalData.getName());
        personalDataDto.setSurname(personalData.getSurname());
        return personalDataDto;
    }

    public List<PersonalDataDto> convertPersonalDatas(final List<PersonalData> personalDatas) {
        return personalDatas.stream().map(this::convertPersonalData).collect(Collectors.toList());
    }
}
