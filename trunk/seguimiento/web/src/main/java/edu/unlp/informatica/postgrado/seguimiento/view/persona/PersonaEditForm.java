package edu.unlp.informatica.postgrado.seguimiento.view.persona;

import java.util.Arrays;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol;
import edu.unlp.informatica.postgrado.seguimiento.validator.BaseEntityForm;

public class PersonaEditForm extends BaseEntityForm<Persona> {

	private static final long serialVersionUID = 7499289901011022854L;	


		
	public PersonaEditForm() {
		
		super("inputForm", new Persona());
		
		add(new TextField<String>("nombre").setLabel(new Model<String>("Nombre")));
		add(new TextField<String>("userName").setLabel(new Model<String>("userName")));
		add(new TextField<String>("password").setLabel(new Model<String>("password")));
		add(new CheckBox("habilitado").setLabel(new Model<String>("habilitado")));
		
		ListMultipleChoice<Rol> roles;
		add(roles = new ListMultipleChoice<Rol>("roles"));
		roles.setChoices(Arrays.asList(Rol.values()));
		roles.setLabel(new Model<String>("Roles"));
	}
}
