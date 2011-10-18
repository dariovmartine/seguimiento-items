package edu.unlp.informatica.postgrado.seguimiento.view.proyecto;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.validator.BaseEntityForm;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

public class ProyectoEditForm extends BaseEntityForm<Proyecto> {

	private static final long serialVersionUID = 7499289901011022854L;	

	private FormComponent<String> textField = null;
		
	private ListChoice<Persona> choice;
	
	public ProyectoEditForm() {
		
		super("inputForm", new Proyecto());

		textField = new TextField<String>("nombre").setLabel(new Model<String>("Nombre"));
		add(textField);
		
		try {

			add(choice = new ListChoice<Persona>("lider"));
			choice.setChoices(DataSourceLocator.getInstance().getPersonaService().find());
			choice.setLabel(new Model<String>("Lider"));
		} catch (ServiceException e) {
			
		}
	}
	
	
}
