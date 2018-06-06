package pl.zzpwjj.restaurant.common.exceptions;

import pl.zzpwjj.restaurant.common.exceptions.base.RestaurantException;

public class DataSourceException extends RestaurantException {

    public DataSourceException() {
        super();
    }

    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
