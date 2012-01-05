package edu.unlp.informatica.postgrado.seguimiento.item.mapper;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Payload;

@Target({ METHOD, FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotMapper {
	 Class<?>[] groups() default {};
	 
	   Class<? extends Payload>[] payload() default {};
}
