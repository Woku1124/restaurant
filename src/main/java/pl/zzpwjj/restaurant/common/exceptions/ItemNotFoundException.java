package pl.zzpwjj.restaurant.common.exceptions;

import pl.zzpwjj.restaurant.common.exceptions.base.RestaurantException;

public class ItemNotFoundException extends RestaurantException {

    public ItemNotFoundException() {
        super();
    }

    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
