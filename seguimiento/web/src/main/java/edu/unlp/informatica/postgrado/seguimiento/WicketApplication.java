package edu.unlp.informatica.postgrado.seguimiento;

import org.apache.wicket.Page;
import org.apache.wicket.authorization.strategies.CompoundAuthorizationStrategy;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see edu.unlp.informatica.postgrado.seguimiento.Start#main(String[])
 *
 * @author dariovmartine
 *
 **/
public class WicketApplication extends AuthenticatedWebApplication {

	boolean isInitialized = false;

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
	public Class<? extends Page> getHomePage() {
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

	public static class WebRoleAuthorizationStrategy extends CompoundAuthorizationStrategy {
		
		private WebAnnotationsRoleAuthorizationStrategy webAnnotationsRoleAuthorizationStrategy;
		
		public WebRoleAuthorizationStrategy(final IRoleCheckingStrategy roleCheckingStrategy)
		{
			super();
			webAnnotationsRoleAuthorizationStrategy = 
				new WebAnnotationsRoleAuthorizationStrategy(roleCheckingStrategy);
			add(webAnnotationsRoleAuthorizationStrategy);
		}

		public WebAnnotationsRoleAuthorizationStrategy getWebAnnotationsRoleAuthorizationStrategy() {
			return webAnnotationsRoleAuthorizationStrategy;
		}
	}
}
