package edu.unlp.informatica.postgrado.seguimiento.view.configuracionestado;

import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.Model;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.validator.BaseEntityForm;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

public class ConfiguracionEstadoEditForm extends BaseEntityForm<ConfiguracionEstado> {

	private static final long serialVersionUID = 7499289901011022854L;

	private ListChoice<Estado> estado;

	private ListMultipleChoice<Estado> proximosEstados;
		
	public ConfiguracionEstadoEditForm() {
		
		super("inputForm", new ConfiguracionEstado());		
		
		
		
		try {

			add(estado = new ListChoice<Estado>("estado"));
			estado.setChoices(DataSourceLocator.getInstance().getEstadoService().find());
			estado.setLabel(new Model<String>("Estado"));
			estado.setEnabled(false);
		} catch (ServiceException e) {
			
		}

		try {

			add(proximosEstados = new ListMultipleChoice<Estado>("proximosEstados"));
			proximosEstados.setChoices(DataSourceLocator.getInstance().getEstadoService().find());
			proximosEstados.setLabel(new Model<String>("Próximos estados"));
		} catch (ServiceException e) {
			
		}		
	}	
}
