package edu.unlp.informatica.postgrado.seguimiento.view.configuracionitem;

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

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

public class ConfiguracionItemEditPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3342982114735537862L;

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
				
				ConfiguracionItem configuracionItem;
				try {
					
					if (newVersion.getId() != null) {
						
						configuracionItem = DataSourceLocator.getInstance().getConfiguracionItemService()
									.getById(newVersion.getId());
						//configuracionItem.setEstado(newVersion.getEstado());
						configuracionItem.setProximosEstados(newVersion.getProximosEstados());
						//configuracionItem.setTipoItem(newVersion.getTipoItem());
						
						DataSourceLocator.getInstance().getConfiguracionItemService().update(configuracionItem);
					} else {
						
						configuracionItem = newVersion;
						DataSourceLocator.getInstance().getConfiguracionItemService().save(configuracionItem);
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
	
	public void setConfiguracionItemId(Long configuracionItemId) {

		ConfiguracionItem configuracionItem = new ConfiguracionItem();
		try {
			
			if (configuracionItemId != null) {
				configuracionItem = DataSourceLocator.getInstance().getConfiguracionItemService().getById(configuracionItemId);
			}
		} catch (ServiceException e) {
			
		}	

		formInput.setModel(new CompoundPropertyModel<ConfiguracionItem>(configuracionItem));
	}
}
