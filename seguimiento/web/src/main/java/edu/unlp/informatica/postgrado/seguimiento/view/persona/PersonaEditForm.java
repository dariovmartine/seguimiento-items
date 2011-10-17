package edu.unlp.informatica.postgrado.seguimiento.view.persona;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.validator.BaseEntityForm;

public class PersonaEditForm extends BaseEntityForm<Persona> {

	private static final long serialVersionUID = 7499289901011022854L;	

	private FormComponent<String> textField = null;
		
	public PersonaEditForm() {
		
		super("inputForm", new Persona());

		textField = new TextField<String>("nombre").setLabel(new Model<String>("Nombre"));
		add(textField);
		
	}
	
	
}
