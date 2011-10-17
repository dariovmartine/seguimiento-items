package edu.unlp.informatica.postgrado.seguimiento.view.item;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.validator.BaseEntityForm;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

public class ItemEditForm extends BaseEntityForm<Item> {

	private static final long serialVersionUID = 7499289901011022854L;	

	private FormComponent<String> textField = null;
	
	private ListChoice<Estado> choice;
	
	public ItemEditForm() {
		
		super("inputForm", new Item());

		textField = new TextField<String>("nombre").setLabel(new Model<String>("Nombre"));
		add(textField);
		
		try {

			add(choice = new ListChoice<Estado>("estado"));
			choice.setChoices(DataSourceLocator.getInstance().getEstadoService().find());
			choice.setLabel(new Model<String>("Estado"));
		} catch (ServiceException e) {
			
		}
	}	
}
