package edu.unlp.informatica.postgrado.item.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.logging.Logger;

public class ValidUserNameValidator implements
		ConstraintValidator<ValidUserName, String> {

	private Logger log = Logger.getLogger(ValidUserNameValidator.class.getName());

	private String[] forbiddenNames = { "Michal", "Mikey", "Mickey", "M1key",
			"M1ckey" };

	@Override
	public void initialize(ValidUserName firstUpper) {
		// See JSR 303 Section 2.4.1 for sample implementation.
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		log.info("Validating");

		if (value == null || value.length() == 0) {
			return true;
		}

		return isNotForbidden(value);
	}

	private boolean isNotForbidden(String name) {
		return !isForbidden(name);
	}

	private boolean isForbidden(String name) {
		for (String forbiddenName : forbiddenNames) {
			if (name.equals(forbiddenName)) {
				return true;
			}
		}
		return false;
	}
}
