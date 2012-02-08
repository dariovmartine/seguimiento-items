package edu.unlp.informatica.postgrado.seguimiento.view.proyecto;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.WildcardListModel;
import org.apache.wicket.util.string.JavaScriptUtils;

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

			lider = new ListChoice<Persona>("lider", 
					new WildcardListModel<Persona>(DataSourceLocator.getInstance().getPersonaService().find()))
			{
				private static final long serialVersionUID = -2056220703101217095L;

				@Override
				public void updateModel() {
					try {
						super.updateModel();	
					} catch (Exception e) {
						this.getForm().error(e.getCause().getMessage());
					}
					
				}
				
				
			};
			
			lider.setLabel(new Model<String>("Lider"));
			add(lider);
		} catch (ServiceException e) {
			
		}
		
		try {

			integrantes = new ListMultipleChoice<Persona>("integrantes") {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1696235192122623061L;

				@Override
				public void updateModel() {
					try {
						super.updateModel();	
					} catch (Exception e) {
						this.getForm().error(e.getCause().getMessage());
					}
					
				}
				
			};
			integrantes.setChoices(DataSourceLocator.getInstance().getPersonaService().find());
			integrantes.setLabel(new Model<String>("Integrantes"));
			add(integrantes);
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
