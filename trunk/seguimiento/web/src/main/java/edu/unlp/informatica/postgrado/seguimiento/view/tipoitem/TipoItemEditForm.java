package edu.unlp.informatica.postgrado.seguimiento.view.tipoitem;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import edu.unlp.informatica.postgrado.seguimiento.validator.BaseEntityForm;

public class TipoItemEditForm extends BaseEntityForm<TipoItem> {

	private static final long serialVersionUID = 7499289901011022854L;	

	private FormComponent<String> textField = null;
		
	public TipoItemEditForm() {
		
		super("inputForm", new TipoItem());

		textField = new TextField<String>("nombre").setLabel(new Model<String>("Nombre"));
		add(textField);
		
	}
	
	
}
