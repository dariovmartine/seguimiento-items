package edu.unlp.informatica.postgrado.seguimiento.view.estado;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.validator.BaseEntityForm;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

public class EstadoEditForm extends BaseEntityForm<Estado> {

	private static final long serialVersionUID = 7499289901011022854L;	

	private FormComponent<String> textField = null;
		
	public EstadoEditForm() {
		
		super("inputForm", new Estado());

		textField = new TextField<String>("name").setLabel(new Model<String>("Nombre"));
		add(textField);
		
	}
	
	
}
