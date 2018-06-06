package pl.zzpwjj.restaurant.common.exceptions;

import pl.zzpwjj.restaurant.common.exceptions.base.RestaurantException;

public class InvalidParametersException extends RestaurantException {

    public InvalidParametersException() {
        super();
    }

    public InvalidParametersException(String message) {
        super(message);
    }

    public InvalidParametersException(String message, Throwable cause) {
        super(message, cause);
    }
}
