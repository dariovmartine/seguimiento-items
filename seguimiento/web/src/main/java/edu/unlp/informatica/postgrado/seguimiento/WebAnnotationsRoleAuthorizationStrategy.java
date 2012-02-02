package edu.unlp.informatica.postgrado.seguimiento;

import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AnnotationsRoleAuthorizationStrategy;
import org.apache.wicket.request.component.IRequestableComponent;

import edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol;

public class WebAnnotationsRoleAuthorizationStrategy extends
		AnnotationsRoleAuthorizationStrategy {

	public WebAnnotationsRoleAuthorizationStrategy(
			IRoleCheckingStrategy roleCheckingStrategy) {
		super(roleCheckingStrategy);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T extends IRequestableComponent> boolean isInstantiationAuthorized(
			Class<T> componentClass) {
		// We are authorized unless we are found not to be
		boolean authorized = true;

		// Check class annotation first because it is more specific than package annotation
		final WebAuthorizeInstantiation classAnnotation = componentClass.getAnnotation(WebAuthorizeInstantiation.class);
		if (classAnnotation != null)
		{
			Rol[] roles = classAnnotation.value();
			authorized = hasAny(new Roles(roles.toString()));
		}
		else
		{
			// Check package annotation if there is no one on the the class
			final Package componentPackage = componentClass.getPackage();
			if (componentPackage != null)
			{
				final WebAuthorizeInstantiation packageAnnotation = componentPackage.getAnnotation(WebAuthorizeInstantiation.class);
				if (packageAnnotation != null)
				{
					Rol[] roles = packageAnnotation.value();
					authorized = hasAny(new Roles(roles.toString()));
				}
			}
		}

		return authorized;

	}

	
}
