package edu.unlp.informatica.postgrado.seguimiento.view.listado;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

public class FormInput extends Form {

	private static final long serialVersionUID = 7499289901011022854L;
	
	private String itemId;

	
	public FormInput(String name) {
		super(name);// , new CompoundPropertyModel<FormInputModel>(new
					// FormInputModel()));

		FormComponent<String> textField = new TextField<String>("firstname").setRequired(true).setLabel(
				new Model<String>("String"));
		//textField.getModel().setObject(itemId);
		add(textField);
	}

	public void setItemId(String id2) {
		this.itemId = id2;
		
	}

}
