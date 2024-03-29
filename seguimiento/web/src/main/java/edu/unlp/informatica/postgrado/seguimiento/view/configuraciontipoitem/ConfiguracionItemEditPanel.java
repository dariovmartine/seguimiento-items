package edu.unlp.informatica.postgrado.seguimiento.view.configuraciontipoitem;

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
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.*;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

@WebAuthorizeInstantiation({ ADMINISTRADOR, LIDER_DE_PROYECTO})
public class ConfiguracionItemEditPanel extends Panel {

	private static final long serialVersionUID = -5255144110728284649L;
	
	private ConfiguracionItemEditForm formInput;

	@SuppressWarnings("serial")
	public ConfiguracionItemEditPanel(String id) {
		
		super(id);
		// create a feedback panel
		final Component feedback = new FeedbackPanel("feedback").setOutputMarkupPlaceholderTag(true);
		add(feedback);
		
		add(formInput = new ConfiguracionItemEditForm());
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
				ConfiguracionItem newVersion = (ConfiguracionItem) getForm().getModelObject();
								
				try {
					
					if (newVersion.getId() != null) {
						
						DataSourceLocator.getInstance().getConfiguracionItemService().update(newVersion);
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
	
	public void setConfiguracionItemId(Long configuracionItemId) {

		ConfiguracionItem ConfiguracionItem = new ConfiguracionItem();
		try {
			
			if (configuracionItemId != null) {
				ConfiguracionItem = DataSourceLocator.getInstance().getConfiguracionItemService().getById(configuracionItemId);
			}
		} catch (ServiceException e) {
			
		}	

		formInput.setModel(new CompoundPropertyModel<ConfiguracionItem>(ConfiguracionItem));
	}
}
