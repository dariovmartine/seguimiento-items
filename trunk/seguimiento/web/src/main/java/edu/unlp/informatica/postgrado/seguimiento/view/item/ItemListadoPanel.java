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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;




/**
 * page that demonstrates dataview and sorting
 * 
 * @author dariovmartine
 * 
 */
public class ItemListadoPanel extends Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6639795032464660258L;
	
	@SpringBean(name="sortableItemDataProvider")
	ItemSortableDataProvider sortableItemDataProvider;
	
	private ItemEditPanel itemEditPanel = null;
	
	private DataView<edu.unlp.informatica.postgrado.seguimiento.item.model.Item> dataView = null; 

	/**
	 * constructor
	 */
	@SuppressWarnings("serial")
	public ItemListadoPanel(String id) {
		
		super(id);
		setOutputMarkupId(true);
		final Label result;
		add(result = new Label("result", new PropertyModel<String>(this, "result")));
		result.setOutputMarkupId(true);
	
		final ModalWindow itemEditWindow;
		add(itemEditWindow = new ModalWindow("modal2"));
				
		itemEditWindow.setContent(itemEditPanel = new ItemEditPanel(itemEditWindow.getContentId()));
		itemEditWindow.setTitle("Item");
		itemEditWindow.setCookieName("modal-2");
		
		itemEditWindow.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

			public boolean onCloseButtonClicked(AjaxRequestTarget target) {
				// setResult("Modal window 2 - close button");
				return true;
			}
		});

		itemEditWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

			/**
			 * 
			 */
			public void onClose(AjaxRequestTarget target) {
				// target.add(result);
			}
		});
		
		
			dataView = new DataView<edu.unlp.informatica.postgrado.seguimiento.item.model.Item>("sorting", getSortableItemDataProvider()) {
			
			@Override
			protected void populateItem(
					final Item<edu.unlp.informatica.postgrado.seguimiento.item.model.Item> item) {
				final edu.unlp.informatica.postgrado.seguimiento.item.model.Item itemSel = item
						.getModelObject();
				
				item.add(new Label("contactid", String.valueOf(itemSel.getId())));
				item.add(new Label("firstname", itemSel.getName()));
				item.add(new Label("lastname", itemSel.getName()));
				item.add(new Label("homephone", itemSel.getName()));
				item.add(new Label("cellphone", itemSel.getState()));

				item.add(AttributeModifier.replace("class",
						new AbstractReadOnlyModel<String>() {

							@Override
							public String getObject() {
								return (item.getIndex() % 2 == 1) ? "even"
										: "odd";
							}
						}));
				item.add(new AjaxLink<Void>("showModal2") {
					
					@Override
					public void onClick(AjaxRequestTarget target) {
						itemEditPanel.setItemSel(itemSel);
						itemEditWindow.show(target);
					}
				});

			}
		};

		dataView.setItemsPerPage(8);
		
		add(new OrderByBorder("orderByFirstName", "firstName",
				getSortableItemDataProvider()) {

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});

		add(new OrderByBorder("orderByLastName", "lastName",
				getSortableItemDataProvider()) {

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});

		add(dataView);

		add(new PagingNavigator("navigator", dataView));
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	private String result;

	public ItemSortableDataProvider getSortableItemDataProvider() {
		return sortableItemDataProvider;
	}

	public void setSortableItemDataProvider(
			ItemSortableDataProvider sortableItemDataProvider) {
		this.sortableItemDataProvider = sortableItemDataProvider;
	}

	public DataView<edu.unlp.informatica.postgrado.seguimiento.item.model.Item> getDataView() {
		return dataView;
	}
}
