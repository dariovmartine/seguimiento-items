package edu.unlp.informatica.postgrado.seguimiento.view.configuracionestado;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import edu.unlp.informatica.postgrado.seguimiento.WebAuthorizeInstantiation;
import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;
import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.*;

@WebAuthorizeInstantiation({ ADMINISTRADOR, LIDER_DE_PROYECTO})
public class ConfiguracionEstadoEditPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3342982114735537862L;

	private ConfiguracionEstadoEditForm formInput;
	
	@SuppressWarnings("serial")
	public ConfiguracionEstadoEditPanel(String id) {
		
		super(id);
		// create a feedback panel
		final Component feedback = new FeedbackPanel("feedback").setOutputMarkupPlaceholderTag(true);
		add(feedback);
		
		add(formInput = new ConfiguracionEstadoEditForm());
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
				ConfiguracionEstado newVersion = (ConfiguracionEstado) getForm().getModelObject();
				
				try {
					
					if (newVersion.getId() != null) {
						
						DataSourceLocator.getInstance().getConfiguracionEstadoService().update(newVersion);
					} else {
						target.appendJavaScript("alert('No puede crear nuevas configuraciones de estado desde esta ventana!');");
					}
					
					// Esto es para que se refresque la grilla de datos
					target.add(this.getForm().getParent().getParent().getParent());
					
					((ModalWindow) getForm().getParent().getParent()).close(target);	
				} catch (ServiceException e) {
					
					feedback.error(e.getCause().getCause().getCause().getLocalizedMessage());
					target.add(feedback);
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target) {
				
				target.add(feedback);				
			}			
		});
	}
	
	public void setConfiguracionEstadoId(Long configuracionEstadoId) {

		ConfiguracionEstado configuracionEstado = new ConfiguracionEstado();
		try {
			
			if (configuracionEstadoId != null) {
				configuracionEstado = DataSourceLocator.getInstance().getConfiguracionEstadoService().getById(configuracionEstadoId);
			}
		} catch (ServiceException e) {
			
		}	

		formInput.setModel(new CompoundPropertyModel<ConfiguracionEstado>(configuracionEstado));
	}
}
