package aviationModelling.validators;

import aviationModelling.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    @Autowired
    UserRepository userRepository;

    public void initialize(UniqueUsername constraint) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {

        return value==null || (userRepository.findByUsername(value)==null);
    }
}
