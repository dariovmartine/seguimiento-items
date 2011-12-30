package edu.unlp.informatica.postgrado.seguimiento.view.item;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.WildcardListModel;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Prioridad;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import edu.unlp.informatica.postgrado.seguimiento.validator.BaseEntityForm;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

public class ItemEditForm extends BaseEntityForm<Item> {

	private static final long serialVersionUID = 7499289901011022854L;	

	private FormComponent<String> titulo;
	
	private FormComponent<String> descripcion;
	
	private DropDownChoice<Estado> estado;
	
	private DropDownChoice<Persona> responsable;
	
	private DropDownChoice<Prioridad> prioridad;
	
	private DropDownChoice<TipoItem> tipoItem;
	
	private DropDownChoice<Proyecto> proyecto;
	
	public DropDownChoice<Proyecto> getProyecto() {
		return proyecto;
	}

	@SuppressWarnings("serial")
	public ItemEditForm() {
		
		super("inputForm", new Item());

		titulo = new TextField<String>("titulo").setLabel(new Model<String>("Nombre"));
		add(titulo);

		descripcion = new TextField<String>("descripcion").setLabel(new Model<String>("Descripcion"));
		add(descripcion);
		
		try {

			add(estado = new DropDownChoice<Estado>("estado",new WildcardListModel<Estado>(DataSourceLocator.getInstance().getEstadoService().find())){

				@Override
				public void updateModel() {
					try {
						super.updateModel();	
					} catch (Exception e) {
						this.getForm().error(e.getCause().getMessage());
					}
					
				}
				
				
			});
			estado.setLabel(new Model<String>("Estado"));
		} catch (ServiceException e) {
			
		}
		
		try {

			add(responsable = new DropDownChoice<Persona>("responsable",DataSourceLocator.getInstance().getPersonaService().find()));
			responsable.setLabel(new Model<String>("Responsable"));
		} catch (ServiceException e) {
			
		}

		try {

			add(prioridad = new DropDownChoice<Prioridad>("prioridad",DataSourceLocator.getInstance().getPrioridadService().find()));
			prioridad.setLabel(new Model<String>("Prioridad"));
		} catch (ServiceException e) {
			
		}

		try {

			add(tipoItem = new DropDownChoice<TipoItem>("tipoItem",DataSourceLocator.getInstance().getTipoItemService().find()));
			tipoItem.setLabel(new Model<String>("Tipo Item"));
		} catch (ServiceException e) {
			
		}

		try {

			add(proyecto = new DropDownChoice<Proyecto>("proyecto",DataSourceLocator.getInstance().getProyectoService().find()));
			proyecto.setLabel(new Model<String>("Proyecto"));
		} catch (ServiceException e) {
			
		}
	}
}
