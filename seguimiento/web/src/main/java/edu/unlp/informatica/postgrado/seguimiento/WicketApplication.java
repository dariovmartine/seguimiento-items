package edu.unlp.informatica.postgrado.seguimiento;

import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.RoleAuthorizationStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see edu.unlp.informatica.postgrado.seguimiento.Start#main(String[])
 */
public class WicketApplication extends AuthenticatedWebApplication {

	boolean isInitialized = false;
	
	/**
	 * Constructor
	 */
	public WicketApplication() {

	}

	@Override
	protected void init() {
		if (!isInitialized) {
			super.init();
			getComponentInstantiationListeners().add(new SpringComponentInjector(this));
			getSecuritySettings().setAuthorizationStrategy(new WebRoleAuthorizationStrategy(this));
			isInitialized = true;
		}		
	}
	
	@Override
	public Class<LoginPage> getHomePage() {
		return LoginPage.class;
	}

	@Override
	protected Class<? extends AuthenticatedWebSession> getWebSessionClass() {
		return SpringWicketWebSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LoginPage.class;
	}

	class WebRoleAuthorizationStrategy extends RoleAuthorizationStrategy {
		
		public WebRoleAuthorizationStrategy(final IRoleCheckingStrategy roleCheckingStrategy)
		{
			super(roleCheckingStrategy);
			add(new WebAnnotationsRoleAuthorizationStrategy(roleCheckingStrategy));
		}
	}
}
