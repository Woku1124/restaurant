package pl.zzpwjj.restaurant.core.taskexample.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.common.exceptions.InvalidParametersException;
import pl.zzpwjj.restaurant.core.taskexample.model.inputs.AddTaskInput;

@Service
public class TasksValidator {

    public void validateAddTaskInput(final AddTaskInput taskDto) throws InvalidParametersException {
        String errors = "";

        // sprawdza czy pierwszy znak title jest wielka litera
        if (!StringUtils.isAllUpperCase(taskDto.getTitle().substring(0,1))) {
            errors += "title - first sign can't be lower case\n";
        }

        // sprawdza czy pierwszy znak description jest wielka litera
        if (!StringUtils.isAllUpperCase(taskDto.getDescription().substring(0,1))) {
            errors += "description - first sign can't be lower case\n";
        }

        if (!errors.isEmpty()) {
            throw new InvalidParametersException(errors);
        }
    }
}