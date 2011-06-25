package edu.unlp.informatica.postgrado.seguimiento.view.listado;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;


public class ListadoApplication extends WebApplication
{
	/**
	 * Constructor.
	 */
	public ListadoApplication()
	{
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class< ? extends Page> getHomePage()
	{
		return SortingPage.class;
	}


}
