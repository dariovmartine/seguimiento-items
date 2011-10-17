package edu.unlp.informatica.postgrado.seguimiento.view.persona;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.springframework.stereotype.Component;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;


/**
 * implementation of IDataProvider for contacts that keeps track of sort information
 * 
 * @author dariovmartine
 * 
 */
@Component("sortablePersonaDataProvider")
public class PersonaSortableDataProvider extends SortableDataProvider<Persona> {
		
	private static final long serialVersionUID = -7831455860632228103L;
			
	/**
	 * constructor
	 */
	public PersonaSortableDataProvider()
	{
		super();
		// set default sort
		setSort("nombre", SortOrder.DESCENDING);
	}

	/**
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#iterator(int, int)
	 */
	public Iterator<Persona> iterator(int first, int count)
	{
		try {
			return DataSourceLocator.getInstance().getPersonaService().find(first, count, getSort().toString()).iterator();
		} catch (ServiceException e) {
			return null;
		}
	}

	/**
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#size()
	 */
	public int size()
	{
		try {
			return (int) DataSourceLocator.getInstance().getPersonaService().getCount();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}

	/**
	 * Como el servicio ya me entrega el objeto desconectado puedo 
	 * enviarlo directamente a la interfaz.
	 * 
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#model(java.lang.Object)
	 */
	@SuppressWarnings("serial")
	public IModel<Persona> model(final Persona persona)
	{
		return new LoadableDetachableModel<Persona>(){

			@Override
			protected Persona load() {
				// TODO Auto-generated method stub
				return persona;
			}};
	}
}
