package edu.unlp.informatica.postgrado.seguimiento;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see edu.unlp.informatica.postgrado.seguimiento.Start#main(String[])
 */
public class WicketApplication extends WebApplication {

	/**
	 * Constructor
	 */
	public WicketApplication() {
	}

	@Override
	public void init() {
		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));

	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<HomePage> getHomePage() {
		return HomePage.class;
	}

}
