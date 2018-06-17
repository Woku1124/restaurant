package pl.zzpwjj.restaurant.core;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
abstract public class ValidatorBase<T> {

    abstract public void validate(final T t) throws InvalidParametersException;

    protected String validateName(final String data, final String dataName) {
        String error = "";
        // sprawdza czy pierwszy znak nazwy jest wielka litera
        if (!StringUtils.isAllUpperCase(data.substring(0,1))) {
            error += dataName + " error - first sign can't be lower case\n";
        }

        // sprawdza czy pozostałe znaki nazwy oprócz pierwszego są małymi literami
        if(!data.substring(1, data.length()).chars().allMatch(Character::isLowerCase)) {
            error += dataName + " error - shouldn't contain upper case letters besides the first one\n";
        }

        // sprawdza czy nazwa zawiera tylko litery
        if(!data.chars().allMatch(Character::isLetter)){
            error += dataName + " error - shouldn't contain other signs than letters\n";
        }
        return error;
    }

    protected String validateInteger(final Integer data, final String dataName){
        String error = "";

        //sprawdza czy dane zawierają tylko cyfry
        if(!data.toString().chars().allMatch(Character::isDigit)) {
            error += dataName + " error - should contain only digits\n";
        }
        return error;
    }

    protected String validateEmailAdress(final String email) {
        String error = "";

        //sprawdza czy email jest prawidłowy
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            error += "Email error - not a valid email\n";
        }
        return error;
    }

    protected String validatePhoneNumber(final Long number) {
        String error = "";

        //sprawdza czy numer zawiera tylko cyfry
        if(!number.toString().chars().allMatch(Character::isDigit)) {
            error += "Phone number error - should contain only digits\n";
        }
        //sprawdza czy długość numberu to 9
        if(number.toString().length() != 9) {
            error += "Phone number error - should always have exactly 9 digits\n";
        }
        return error;
    }
}
