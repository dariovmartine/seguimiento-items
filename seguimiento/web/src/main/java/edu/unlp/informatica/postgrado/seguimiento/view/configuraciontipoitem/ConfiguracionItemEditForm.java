package edu.unlp.informatica.postgrado.seguimiento.view.configuraciontipoitem;

import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.validator.BaseEntityForm;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

public class ConfiguracionItemEditForm extends
		BaseEntityForm<ConfiguracionItem> {

	private static final long serialVersionUID = -7324919205048906667L;

	private ListMultipleChoice<Estado> estadosIniciales;

	public ConfiguracionItemEditForm() {

		super("inputForm", new ConfiguracionItem());

		try {

			CheckGroup<Estado> checks = new CheckGroup<Estado>(
					"estadosIniciales");
			
			add(checks);
			ListView<Estado> checksList = new ListView<Estado>(
					"estadosInicialesCheckBox", DataSourceLocator.getInstance().getEstadoService().find()) {
				
				private static final long serialVersionUID = -6087603235109311980L;

				@Override
				protected void populateItem(ListItem<Estado> items) {
					Check<Estado> check = new Check<Estado>("check", items.getModel());
					check.setLabel(new PropertyModel<String>(items.getModel(), "nombre"));
					items.add(check);
					items.add(new SimpleFormComponentLabel("estadoInicial", check));
					
				}
			}.setReuseItems(true);
			checks.add(checksList);
			
		} catch (ServiceException e) {
		}
	}
}
