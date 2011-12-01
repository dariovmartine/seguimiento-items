package edu.unlp.informatica.postgrado.seguimiento.view.proyecto;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import edu.unlp.informatica.postgrado.seguimiento.validator.BaseEntityForm;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

public class ProyectoEditForm extends BaseEntityForm<Proyecto> {

	private static final long serialVersionUID = 7499289901011022854L;	

	private FormComponent<String> textField = null;
		
	private ListChoice<Persona> lider;
	
	private ListMultipleChoice<Persona> integrantes;
	
	private ListMultipleChoice<TipoItem> tipoItemProyecto;
	
	public ProyectoEditForm() {
		
		super("inputForm", new Proyecto());

		textField = new TextField<String>("nombre").setLabel(new Model<String>("Nombre"));
		add(textField);
		
		try {

			add(lider = new ListChoice<Persona>("lider"));
			lider.setChoices(DataSourceLocator.getInstance().getPersonaService().find());
			lider.setLabel(new Model<String>("Lider"));
		} catch (ServiceException e) {
			
		}
		
		try {

			add(integrantes = new ListMultipleChoice<Persona>("integrantes"));
			integrantes.setChoices(DataSourceLocator.getInstance().getPersonaService().find());
			integrantes.setLabel(new Model<String>("Integrantes"));
		} catch (ServiceException e) {
			
		}

		try {

			add(tipoItemProyecto = new ListMultipleChoice<TipoItem>("tipoItemList"));
			tipoItemProyecto.setChoices(DataSourceLocator.getInstance().getTipoItemService().find());
			tipoItemProyecto.setLabel(new Model<String>("Tipos de Items"));
		} catch (ServiceException e) {
			
		}
		
	}
	
	
}
