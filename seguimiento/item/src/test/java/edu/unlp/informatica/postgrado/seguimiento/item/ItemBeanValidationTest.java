package edu.unlp.informatica.postgrado.seguimiento.item;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;

public class ItemBeanValidationTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@Rollback
	public void test() {
		Item bean = new Item();

		Set<ConstraintViolation<Item>> constraintViolations = validator
				.validate(bean);
		assertEquals(7, constraintViolations.size());
		
		System.out.println(constraintViolations.iterator().next().getMessage());
		
	}
}

