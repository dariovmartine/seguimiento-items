package edu.unlp.informatica.postgrado.seguimiento.view.menu;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Application class.
 * 
 * @author Jonathan Locke
 */
public class MenuApplication extends WebApplication
{
	/**
	 * Constructor.
	 */
	public MenuApplication()
	{
	}
	
	@Override
	public void init() {
		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class< ? extends Page> getHomePage()
	{
		return TabbedPanelPage.class;
	}


}
