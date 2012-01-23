package edu.unlp.informatica.postgrado.seguimiento.view.configuraciontipoitem;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.springframework.stereotype.Component;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;


/**
 * implementation of IDataProvider for contacts that keeps track of sort information
 * 
 * @author dariovmartine
 * 
 */
@Component("sortableConfiguracionItemDataProvider")
public class ConfiguracionItemSortableDataProvider extends SortableDataProvider<ConfiguracionItem> {
		
	private static final long serialVersionUID = -7831455860632228103L;
			
	/**
	 * constructor
	 */
	public ConfiguracionItemSortableDataProvider()
	{
		super();
		// set default sort
		setSort("tipoItem", SortOrder.DESCENDING);
	}

	/**
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#iterator(int, int)
	 */
	public Iterator<ConfiguracionItem> iterator(int first, int count)
	{
		try {
			return DataSourceLocator.getInstance().getConfiguracionItemService().find(first, count, getSort().toString()).iterator();
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
			return (int) DataSourceLocator.getInstance().getConfiguracionItemService().getCount();
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
	public IModel<ConfiguracionItem> model(final ConfiguracionItem configuracionItem)
	{
		return new LoadableDetachableModel<ConfiguracionItem>(){

			@Override
			protected ConfiguracionItem load() {
				// TODO Auto-generated method stub
				return configuracionItem;
			}};
	}
}
