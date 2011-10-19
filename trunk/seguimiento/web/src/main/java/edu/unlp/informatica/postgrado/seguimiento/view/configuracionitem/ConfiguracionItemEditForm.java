package edu.unlp.informatica.postgrado.seguimiento.view.configuracionitem;

import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.Model;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import edu.unlp.informatica.postgrado.seguimiento.validator.BaseEntityForm;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

public class ConfiguracionItemEditForm extends BaseEntityForm<ConfiguracionItem> {

	private static final long serialVersionUID = 7499289901011022854L;	

	private ListChoice<TipoItem> tipoItem;

	private ListChoice<Estado> estado;

	private ListMultipleChoice<Estado> proximosEstados;
		
	public ConfiguracionItemEditForm() {
		
		super("inputForm", new ConfiguracionItem());		
		
		try {

			add(tipoItem = new ListChoice<TipoItem>("tipoItem"));
			tipoItem.setChoices(DataSourceLocator.getInstance().getTipoItemService().find());
			tipoItem.setLabel(new Model<String>("TipoItem"));
		} catch (ServiceException e) {
			
		}
		
		try {

			add(estado = new ListChoice<Estado>("estado"));
			estado.setChoices(DataSourceLocator.getInstance().getEstadoService().find());
			estado.setLabel(new Model<String>("Estado"));
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
