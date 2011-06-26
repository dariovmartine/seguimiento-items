package edu.unlp.informatica.postgrado.seguimiento.view.listado;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;


public class ListadoApplication extends WebApplication
{
	/**
	 * Constructor.
	 */
	public ListadoApplication()
	{}
	
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
		return SortingPage.class;
	}


}
