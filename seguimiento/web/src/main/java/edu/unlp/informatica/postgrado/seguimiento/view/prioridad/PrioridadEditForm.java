package edu.unlp.informatica.postgrado.seguimiento.view.prioridad;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Prioridad;
import edu.unlp.informatica.postgrado.seguimiento.validator.BaseEntityForm;

public class PrioridadEditForm extends BaseEntityForm<Prioridad> {

	private static final long serialVersionUID = 7499289901011022854L;	

	private FormComponent<String> textField = null;
		
	public PrioridadEditForm() {
		
		super("inputForm", new Prioridad());

		textField = new TextField<String>("nombre").setLabel(new Model<String>("Nombre"));
		add(textField);
		
	}
	
	
}
