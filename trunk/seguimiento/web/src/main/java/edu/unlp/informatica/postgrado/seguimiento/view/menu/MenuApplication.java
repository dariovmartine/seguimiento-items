package edu.unlp.informatica.postgrado.seguimiento.view.menu;

import org.apache.wicket.Page;

import edu.unlp.informatica.postgrado.seguimiento.WicketApplication;

/**
 * Application class.
 * 
 * @author Jonathan Locke
 */
public class MenuApplication extends WicketApplication
{
	

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends Page> getHomePage()
	{
		return TabbedPanelPage.class;
	}
}
