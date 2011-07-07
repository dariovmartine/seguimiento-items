package edu.unlp.informatica.postgrado.seguimiento.view.listado;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ItemService;

public class FormInput extends Form<edu.unlp.informatica.postgrado.seguimiento.item.model.Item> {

	private static final long serialVersionUID = 7499289901011022854L;	

	private FormComponent<String> textField = null;
	
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

	@SpringBean(name="itemService")
	ItemService itemService;
	
	public FormInput() {
		super("inputForm" );// , new CompoundPropertyModel<FormInputModel>(new FormInputModel()));

		textField = new TextField<String>("firstname").setRequired(true).setLabel(
				new Model<String>("String"));
		
		add(textField);
	}

	/**
	 * @return the textField
	 */
	public FormComponent<String> getTextField() {
		return textField;
	}

	/**
	 * @param textField the textField to set
	 */
	public void setTextField(FormComponent<String> textField) {
		this.textField = textField;
	}

	public void setItemSel(Item itemSel) {
		this.textField.setModel(new Model<String>());
		Item i = this.getItemService().getById(itemSel.getId());
		this.textField.getModel().setObject(i.getName());
		
	}

	/**
	 * @return the itemService
	 */
	public ItemService getItemService() {
		return itemService;
	}

	/**
	 * @param itemService the itemService to set
	 */
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	

}
