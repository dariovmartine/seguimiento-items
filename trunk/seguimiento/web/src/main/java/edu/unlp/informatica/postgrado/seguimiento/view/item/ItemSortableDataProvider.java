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
package edu.unlp.informatica.postgrado.seguimiento.view.item;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ItemService;


/**
 * implementation of IDataProvider for contacts that keeps track of sort information
 * 
 * @author dariovmartine
 * 
 */
@Component("sortableItemDataProvider")
public class ItemSortableDataProvider extends SortableDataProvider<Item> {
		
	private static final long serialVersionUID = -7831455860632228103L;
	
	@Autowired
	ItemService itemService;
	
	/**
	 * constructor
	 */
	public ItemSortableDataProvider()
	{
		super();
		// set default sort
		setSort("firstName", SortOrder.DESCENDING);
	}

	/**
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#iterator(int, int)
	 */
	public Iterator<Item> iterator(int first, int count)
	{
		return getItemService().find(first, count, getSort().toString()).iterator();
	}

	/**
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#size()
	 */
	public int size()
	{
		return getItemService().getCount();
	}

	/**
	 * Como el servicio ya me entrega el objeto desconectado puedo 
	 * enviarlo directamente a la interfaz.
	 * 
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#model(java.lang.Object)
	 */
	public IModel<Item> model(final Item item)
	{
		return new LoadableDetachableModel<Item>(){

			private static final long serialVersionUID = 8967269923476817174L;

			@Override
			protected Item load() {
				// TODO Auto-generated method stub
				return item;
			}};
	}
	
	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
}
