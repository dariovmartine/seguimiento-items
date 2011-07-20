package edu.unlp.informatica.postgrado.seguimiento.view.listado;

import java.util.Map;

import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.panel.Panel;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;

public class ItemEditPanel extends Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3342982114735537862L;
	
	private ItemEditForm formInput = new ItemEditForm();


	/**
	 * @param id
	 */
	public ItemEditPanel(String id)
	{
		super(id);
		
		add(getFormInput());
		formInput.add(new DateTimeField("dateTimeField")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * @see org.apache.wicket.extensions.yui.calendar.DateTimeField#configure(java.util.Map)
			 */
			@Override
			protected void configure(Map<String, Object> widgetProperties)
			{
				super.configure(widgetProperties);
				// IE 6 breaks layout with iframe - is that a YUI bug?
				widgetProperties.put("iframe", false);
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
	 * @param formInput the formInput to set
	 */
	public void setFormInput(ItemEditForm formInput) {
		this.formInput = formInput;
	}

	public void setItemSel(Item itemSel) {
		this.getFormInput().setItemSel(itemSel);
	}
}
