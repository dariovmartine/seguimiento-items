package edu.unlp.informatica.postgrado.seguimiento.item.service;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ METHOD, FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MappingOptions {

	
	Class<?>[] exclude() default {};
	
	int order() default -1;
	
}
