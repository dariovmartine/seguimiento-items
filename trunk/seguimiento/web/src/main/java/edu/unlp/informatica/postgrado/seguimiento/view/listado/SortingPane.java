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
public class SortingPane extends Panel {
	private static final long serialVersionUID = 1L;
	
	/*
	 * ojo con esto: The 'subject' instance of the MyVeryLargeObjectDao class 
	 * will be serialized into the session with each page version. 
	 * This can get out of hand, because with some business object models, 
	 * the attached object can become very large. For this we 
	 * introduced DetachableModels, which will retrieve the data 
	 * from the database when needed, and will 
	 * clean up when the data is not needed 
	 * (at the end of the request for instance).
	 **/
	@SpringBean(name="sortableItemDataProvider")
	SortableItemDataProvider sortableItemDataProvider;
	
	private ModalWindow modal2;
		
	private ModalPanel1 modalPanel1;

	/**
	 * constructor
	 */
	public SortingPane(String id) {
		super(id);
		final Label result;
		add(result = new Label("result", new PropertyModel<String>(this,
				"result")));
		result.setOutputMarkupId(true);


		add(modal2 = new ModalWindow("modal2"));
		modalPanel1 = new ModalPanel1(modal2.getContentId());

		modal2.setContent(getModalPanel1());
		modal2.setTitle("This is modal window with panel content.");
		modal2.setCookieName("modal-2");

		modal2.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8779902619698219539L;

			public boolean onCloseButtonClicked(AjaxRequestTarget target) {
				setResult("Modal window 2 - close button");
				return true;
			}
		});

		modal2.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3646057969858558792L;

			public void onClose(AjaxRequestTarget target) {
				target.add(result);
			}
		});
		
		

		final DataView<edu.unlp.informatica.postgrado.seguimiento.item.model.Item> dataView = new DataView<edu.unlp.informatica.postgrado.seguimiento.item.model.Item>(
				"sorting", getSortableItemDataProvider()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(
					final Item<edu.unlp.informatica.postgrado.seguimiento.item.model.Item> item) {
				final edu.unlp.informatica.postgrado.seguimiento.item.model.Item itemSel = item
						.getModelObject();
				// item.add(new ActionPanel("actions", item.getModel()));
				item.add(new Label("contactid", String.valueOf(itemSel.getId())));
				item.add(new Label("firstname", itemSel.getName()));
				item.add(new Label("lastname", itemSel.getName()));
				item.add(new Label("homephone", itemSel.getName()));
				item.add(new Label("cellphone", itemSel.getState()));

				item.add(AttributeModifier.replace("class",
						new AbstractReadOnlyModel<String>() {
							private static final long serialVersionUID = 1L;

							@Override
							public String getObject() {
								return (item.getIndex() % 2 == 1) ? "even"
										: "odd";
							}
						}));
				item.add(new AjaxLink<Void>("showModal2") {
					
					private static final long serialVersionUID = -8563436483693662304L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						modalPanel1.setItemSel(itemSel);
						modal2.show(target);
					}
				});

			}
		};

		dataView.setItemsPerPage(8);

		add(new OrderByBorder("orderByFirstName", "firstName",
				getSortableItemDataProvider()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});

		add(new OrderByBorder("orderByLastName", "lastName",
				getSortableItemDataProvider()) {
			private static final long serialVersionUID = 1L;

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

	public SortableItemDataProvider getSortableItemDataProvider() {
		return sortableItemDataProvider;
	}

	public void setSortableItemDataProvider(
			SortableItemDataProvider sortableItemDataProvider) {
		this.sortableItemDataProvider = sortableItemDataProvider;
	}

	/**
	 * @return the modalPanel1
	 */
	public ModalPanel1 getModalPanel1() {
		return modalPanel1;
	}

}
