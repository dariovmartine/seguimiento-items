package edu.unlp.informatica.postgrado.seguimiento.view.proyecto;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.validator.BaseEntityForm;

public class ProyectoEditForm extends BaseEntityForm<Proyecto> {

	private static final long serialVersionUID = 7499289901011022854L;	

	private FormComponent<String> textField = null;
		
	public ProyectoEditForm() {
		
		super("inputForm", new Proyecto());

		textField = new TextField<String>("nombre").setLabel(new Model<String>("Nombre"));
		add(textField);
		
	}
	
	
}
