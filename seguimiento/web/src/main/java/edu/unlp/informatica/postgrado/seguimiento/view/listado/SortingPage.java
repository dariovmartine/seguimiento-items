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
package edu.unlp.informatica.postgrado.seguimiento.view.listado;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Controller;



/**
 * page that demonstrates dataview and sorting
 * 
 * @author igor
 * 
 */
public class SortingPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	@SpringBean(name="sortableItemDataProvider")
	SortableItemDataProvider sortableItemDataProvider;
	
	/**
	 * constructor
	 */
	public SortingPage()
	{
		
		final DataView<edu.unlp.informatica.postgrado.seguimiento.view.Item> dataView 
			= new DataView<edu.unlp.informatica.postgrado.seguimiento.view.Item>("sorting", getSortableItemDataProvider())
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<edu.unlp.informatica.postgrado.seguimiento.view.Item> item)
			{
				edu.unlp.informatica.postgrado.seguimiento.item.model.Item itemSel = item.getModelObject();
				//item.add(new ActionPanel("actions", item.getModel()));
				item.add(new Label("contactid", String.valueOf(itemSel.getId())));
				item.add(new Label("firstname", itemSel.getName()));
				item.add(new Label("lastname", itemSel.getName()));
				item.add(new Label("homephone", itemSel.getName()));
				item.add(new Label("cellphone", itemSel.getState()));

//				item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>()
//				{
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					public String getObject()
//					{
//						return (item.getIndex() % 2 == 1) ? "even" : "odd";
//					}
//				}));
			}
		};

		dataView.setItemsPerPage(8);

		add(new OrderByBorder("orderByFirstName", "firstName", getSortableItemDataProvider())
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged()
			{
				dataView.setCurrentPage(0);
			}
		});

		add(new OrderByBorder("orderByLastName", "lastName", getSortableItemDataProvider())
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged()
			{
				dataView.setCurrentPage(0);
			}
		});

		add(dataView);

		add(new PagingNavigator("navigator", dataView));
	}

	public SortableItemDataProvider getSortableItemDataProvider() {
		return sortableItemDataProvider;
	}

	public void setSortableItemDataProvider(
			SortableItemDataProvider sortableItemDataProvider) {
		this.sortableItemDataProvider = sortableItemDataProvider;
	}
	
	
	
}
