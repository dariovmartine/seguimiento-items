package edu.unlp.informatica.postgrado.seguimiento.view.estado;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import edu.unlp.informatica.postgrado.seguimiento.WebAuthorizeInstantiation;
import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.*;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

@WebAuthorizeInstantiation({ ADMINISTRADOR, LIDER_DE_PROYECTO})
public class EstadoEditPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3342982114735537862L;

	private EstadoEditForm formInput;

	@SuppressWarnings("serial")
	public EstadoEditPanel(String id) {
		
		super(id);
		// create a feedback panel
		final Component feedback = new FeedbackPanel("feedback").setOutputMarkupPlaceholderTag(true);
		add(feedback);
		
		add(formInput = new EstadoEditForm());
		formInput.setEditMode(true);
		formInput.add(new AjaxLink<Void>("closeCancel") {
			
			@Override
			public void onClick(AjaxRequestTarget target) {

				((ModalWindow) this.getParent().getParent().getParent())
						.close(target);
			}
		});
		
		
		formInput.add(new AjaxFormSubmitBehavior(formInput, "onsubmit")
		{
			@Override
			protected IAjaxCallDecorator getAjaxCallDecorator()
			{
				return new AjaxCallDecorator()
				{
					public CharSequence decorateScript(Component c, CharSequence script)
					{
						return script + "return false;";
					}
				};
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				Estado newVersion = (Estado) getForm().getModelObject();
				
				Estado estado;
				try {
					
					if (newVersion.getId() != null) {
						
						estado = DataSourceLocator.getInstance().getEstadoService()
									.getById(newVersion.getId());
						estado.setNombre(newVersion.getNombre());
						
						DataSourceLocator.getInstance().getEstadoService().update(estado);
					} else {
						
						estado = newVersion;
						DataSourceLocator.getInstance().getEstadoService().save(estado);
					}				
					
					// Esto es para que se refresque la grilla de datos
					target.add(this.getForm().getParent().getParent().getParent());
					
					((ModalWindow) getForm().getParent().getParent()).close(target);	
				} catch (ServiceException e) {
					
					target.appendJavaScript("alert('" +	e.getCause().getCause().getCause().getLocalizedMessage() + "');");
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target) {
				
				target.add(feedback);				
			}			
		});
	}
	
	public void setEstadoId(Long estadoId) {

		Estado estado = new Estado();
		try {
			
			if (estadoId != null) {
				estado = DataSourceLocator.getInstance().getEstadoService().getById(estadoId);
			}
		} catch (ServiceException e) {
			
		}	

		formInput.setModel(new CompoundPropertyModel<Estado>(estado));
	}
}
