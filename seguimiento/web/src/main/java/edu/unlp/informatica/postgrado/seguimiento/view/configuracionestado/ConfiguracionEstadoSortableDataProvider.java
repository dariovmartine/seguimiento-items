package edu.unlp.informatica.postgrado.seguimiento.view.configuracionestado;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.springframework.stereotype.Component;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;


/**
 * implementation of IDataProvider for contacts that keeps track of sort information
 * 
 * @author dariovmartine
 * 
 */
@Component("sortableConfiguracionEstadoDataProvider")
public class ConfiguracionEstadoSortableDataProvider extends SortableDataProvider<ConfiguracionEstado> {
		
	private static final long serialVersionUID = -7831455860632228103L;
			
	/**
	 * constructor
	 */
	public ConfiguracionEstadoSortableDataProvider()
	{
		super();
		// set default sort
		setSort("tipoItem", SortOrder.DESCENDING);
	}

	/**
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#iterator(int, int)
	 */
	public Iterator<ConfiguracionEstado> iterator(int first, int count)
	{
		try {
			return DataSourceLocator.getInstance().getConfiguracionEstadoService().find(first, count, getSort().toString()).iterator();
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
			return (int) DataSourceLocator.getInstance().getConfiguracionEstadoService().getCount();
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
	public IModel<ConfiguracionEstado> model(final ConfiguracionEstado configuracionItem)
	{
		return new LoadableDetachableModel<ConfiguracionEstado>(){

			@Override
			protected ConfiguracionEstado load() {
				// TODO Auto-generated method stub
				return configuracionItem;
			}};
	}
}
