package edu.unlp.informatica.postgrado.seguimiento.view.configuraciontipoitem;

import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.Model;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.validator.BaseEntityForm;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

public class ConfiguracionItemEditForm extends BaseEntityForm<ConfiguracionItem> {

	private static final long serialVersionUID = -7324919205048906667L;

	private ListMultipleChoice<Estado> estadosIniciales;
		
	public ConfiguracionItemEditForm() {
		
		super("inputForm", new ConfiguracionItem());		

		try {

			add(estadosIniciales = new ListMultipleChoice<Estado>("estadosIniciales"));
			estadosIniciales.setChoices(DataSourceLocator.getInstance().getEstadoService().find());
			estadosIniciales.setLabel(new Model<String>("Estados iniciales"));
		} catch (ServiceException e) {
			
		}		
	}	
}
