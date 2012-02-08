package edu.unlp.informatica.postgrado.seguimiento;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol;

/**
 * @author dariovmartine
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PACKAGE, ElementType.TYPE })
@Documented
@Inherited
public @interface WebAuthorizeInstantiation {
	
	Rol[] value() default { };

	
}
