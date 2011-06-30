package edu.unlp.informatica.postgrado.seguimiento.view.listado;

import java.util.Map;

import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.panel.Panel;

public class ModalPanel1 extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3342982114735537862L;

	/**
	 * @param id
	 */
	public ModalPanel1(String id)
	{
		super(id);
		FormInput formInput = new FormInput("inputForm");
		formInput.setItemId(id);
		add(formInput);
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
}
