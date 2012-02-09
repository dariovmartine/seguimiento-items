package edu.unlp.informatica.postgrado.seguimiento.view.configuracionestado;

import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.validator.BaseEntityForm;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

public class ConfiguracionEstadoEditForm extends BaseEntityForm<ConfiguracionEstado> {

	private static final long serialVersionUID = 7499289901011022854L;
	
				
	public ConfiguracionEstadoEditForm() {
		
		super("inputForm", new ConfiguracionEstado());		
		
		TextField<String> estadoTextField = new TextField<String>("estado");
		estadoTextField.setLabel(new Model<String>("Nombre"));
		estadoTextField.setEnabled(false);
		add(estadoTextField);
				
		try {

			CheckGroup<Estado> checks = new CheckGroup<Estado>(
					"proximosEstados");
			
			add(checks);
			ListView<Estado> checksList = new ListView<Estado>(
					"proximosEstadosCheckBox", DataSourceLocator.getInstance().getEstadoService().find()) {
				
				private static final long serialVersionUID = -6087603235109311980L;

				@Override
				protected void populateItem(ListItem<Estado> items) {
					Check<Estado> check = new Check<Estado>("check", items.getModel());
					check.setLabel(new PropertyModel<String>(items.getModel(), "nombre"));
					items.add(check);
					items.add(new SimpleFormComponentLabel("proximoEstado", check));
					
				}
			}.setReuseItems(true);
			checks.add(checksList);
			
		} catch (ServiceException e) {
			
		}
	}	
}
