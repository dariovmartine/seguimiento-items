package edu.unlp.informatica.postgrado.seguimiento.item.service;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Payload;

@Target({ METHOD  })
@Retention(RetentionPolicy.RUNTIME)
public @interface Updateable {
	 Class<?>[] groups() default {};
	 
	   Class<? extends Payload>[] payload() default {};
}
