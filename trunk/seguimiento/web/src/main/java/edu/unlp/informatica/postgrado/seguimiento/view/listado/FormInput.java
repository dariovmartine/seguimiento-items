package edu.unlp.informatica.postgrado.seguimiento.view.listado;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ItemService;

public class FormInput extends Form<edu.unlp.informatica.postgrado.seguimiento.item.model.Item> {

	private static final long serialVersionUID = 7499289901011022854L;	

	private FormComponent<String> textField = null;
	
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
		this.textField.getModel().setObject(itemSel.getName());
		
	}
	

}
