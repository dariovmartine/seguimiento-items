package edu.unlp.informatica.postgrado.seguimiento.view.item;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.panel.Panel;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

public class ItemEditPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3342982114735537862L;

	private Item item;

	private ItemEditForm formInput = new ItemEditForm();

	public void setItemSel(Item itemSel) {

		Item i = DataSourceLocator.getInstance().getItemService()
				.getById(itemSel.getId());
		getFormInput().setName(i.getName());
		this.getFormInput()
				.getChoice()
				.setChoices(
						DataSourceLocator.getInstance().getEstadoService()
								.getNames());
		item = itemSel;
	}

	public Item getItem() {
		return item;
	}

	public ItemEditPanel(String id) {
		super(id);

		formInput.setEstados(DataSourceLocator.getInstance().getEstadoService()
				.getNames());
		add(getFormInput());

		formInput.add(new DateTimeField("dateTimeField") {

			/**
				 * 
				 */
			private static final long serialVersionUID = 8857477532890851349L;

			/**
			 * @see org.apache.wicket.extensions.yui.calendar.DateTimeField#configure(java.util.Map)
			 */
			@Override
			protected void configure(Map<String, Object> widgetProperties) {
				super.configure(widgetProperties);
				// IE 6 breaks layout with iframe - is that a YUI bug?
				widgetProperties.put("iframe", false);
			}
		});

		formInput.add(new AjaxLink<Void>("closeOK") {
			@Override
			public void onClick(AjaxRequestTarget target) {

				Item i = DataSourceLocator.getInstance().getItemService()
				.getById((((ItemEditPanel) this.getParent().getParent())).getItem().getId());
		
				i.setName(getFormInput().getName());
				//i.setState(getFormInput().getState());
				DataSourceLocator.getInstance().getItemService().save(i);
				
				((ModalWindow) this.getParent().getParent().getParent())
						.close(target);

			}
		});

		formInput.add(new AjaxLink<Void>("closeCancel") {
			@Override
			public void onClick(AjaxRequestTarget target) {

				((ModalWindow) this.getParent().getParent().getParent())
						.close(target);
			}
		});
	}

	/**
	 * @return the formInput
	 */
	public ItemEditForm getFormInput() {
		return formInput;
	}

	/**
	 * @param formInput
	 *            the formInput to set
	 */
	public void setFormInput(ItemEditForm formInput) {
		this.formInput = formInput;
	}

}
