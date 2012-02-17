package edu.unlp.informatica.postgrado.seguimiento;

import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AnnotationsRoleAuthorizationStrategy;
import org.apache.wicket.request.component.IRequestableComponent;

import edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol;

/**
 * @author dariovmartine
 */
public class WebAnnotationsRoleAuthorizationStrategy extends
		AnnotationsRoleAuthorizationStrategy {

	public WebAnnotationsRoleAuthorizationStrategy(
			IRoleCheckingStrategy roleCheckingStrategy) {
		super(roleCheckingStrategy);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
		// We are authorized unless we are found not to be
		boolean authorized = true;
		
		// Check class annotation first because it is more specific than package annotation
		final WebAuthorizeInstantiation classAnnotation = componentClass.getAnnotation(WebAuthorizeInstantiation.class);
		if (classAnnotation != null)
		{
			Roles roles = new Roles();
			for (Rol rol : classAnnotation.value()) {
				roles.add(rol.toString());
			}
		
			authorized = hasAny(roles);
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
					Roles roles = new Roles();
					for (Rol rol : packageAnnotation.value()) {
						roles.add(rol.toString());
					}
					authorized = hasAny(roles);
				}
			}
		}
		
		return authorized;
	}	
}
