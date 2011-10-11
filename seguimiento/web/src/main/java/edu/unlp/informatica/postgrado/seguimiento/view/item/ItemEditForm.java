package edu.unlp.informatica.postgrado.seguimiento.view.item;

import java.util.Collection;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.WildcardListModel;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

public class ItemEditForm extends Form<Item> {

	private static final long serialVersionUID = 7499289901011022854L;	

	private FormComponent<String> textField = null;
	
	private ListMultipleChoice<String> choice;
	
	public ItemEditForm() {
		
		super("inputForm");

		textField = new TextField<String>("name").setRequired(true).setLabel(
				new Model<String>("String"));
		//textField.setModel(new Model<String>());
		add(textField);
		
		add(choice = new ListMultipleChoice<String>("state"));
		choice.setDefaultModel(new WildcardListModel<Collection<String>>());
		choice.setChoices(DataSourceLocator.getInstance().getEstadoService().getNames());
	}
	
	
}
