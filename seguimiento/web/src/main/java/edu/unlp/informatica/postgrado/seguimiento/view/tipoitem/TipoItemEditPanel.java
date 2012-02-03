package edu.unlp.informatica.postgrado.seguimiento.view.tipoitem;

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
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.*;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

@WebAuthorizeInstantiation({ ROLE_USER, ROLE_SUPERVISOR })
public class TipoItemEditPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3342982114735537862L;

	private TipoItemEditForm formInput;

	@SuppressWarnings("serial")
	public TipoItemEditPanel(String id) {
		
		super(id);
		// create a feedback panel
		final Component feedback = new FeedbackPanel("feedback").setOutputMarkupPlaceholderTag(true);
		add(feedback);
		
		add(formInput = new TipoItemEditForm());
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
				TipoItem newVersion = (TipoItem) getForm().getModelObject();
				
				TipoItem tipoItem;
				try {
					
					if (newVersion.getId() != null) {
						
						tipoItem = DataSourceLocator.getInstance().getTipoItemService()
									.getById(newVersion.getId());
						tipoItem.setNombre(newVersion.getNombre());
						
						DataSourceLocator.getInstance().getTipoItemService().update(tipoItem);
					} else {
						
						tipoItem = newVersion;
						DataSourceLocator.getInstance().getTipoItemService().save(tipoItem);
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
	
	public void setTipoItemId(Long tipoItemId) {

		TipoItem tipoItem = new TipoItem();
		try {
			
			if (tipoItemId != null) {
				tipoItem = DataSourceLocator.getInstance().getTipoItemService().getById(tipoItemId);
			}
		} catch (ServiceException e) {
			
		}	

		formInput.setModel(new CompoundPropertyModel<TipoItem>(tipoItem));
	}
	
	
	public TipoItemEditForm getFormInput() {
		return formInput;
	}

}
