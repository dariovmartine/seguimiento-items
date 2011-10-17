/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.unlp.informatica.postgrado.seguimiento.view.tipoitem;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.springframework.stereotype.Component;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;


/**
 * implementation of IDataProvider for contacts that keeps track of sort information
 * 
 * @author dariovmartine
 * 
 */
@Component("sortableTipoItemDataProvider")
public class TipoItemSortableDataProvider extends SortableDataProvider<TipoItem> {
		
	private static final long serialVersionUID = -7831455860632228103L;
			
	/**
	 * constructor
	 */
	public TipoItemSortableDataProvider()
	{
		super();
		// set default sort
		setSort("nombre", SortOrder.DESCENDING);
	}

	/**
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#iterator(int, int)
	 */
	public Iterator<TipoItem> iterator(int first, int count)
	{
		try {
			return DataSourceLocator.getInstance().getTipoItemService().find(first, count, getSort().toString()).iterator();
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
			return (int) DataSourceLocator.getInstance().getTipoItemService().getCount();
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
	public IModel<TipoItem> model(final TipoItem tipoItem)
	{
		return new LoadableDetachableModel<TipoItem>(){

			@Override
			protected TipoItem load() {
				// TODO Auto-generated method stub
				return tipoItem;
			}};
	}
}
