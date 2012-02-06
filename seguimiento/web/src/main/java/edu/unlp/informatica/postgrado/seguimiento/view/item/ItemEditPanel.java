package edu.unlp.informatica.postgrado.seguimiento.view.item;

import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.ADMINISTRADOR;
import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.DESARROLLADOR;
import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.LIDER_DE_PROYECTO;
import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.USUARIO;

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
import edu.unlp.informatica.postgrado.seguimiento.WicketApplication;
import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;
import edu.unlp.informatica.postgrado.seguimiento.view.tipoitem.TipoItemEditPanel;

@WebAuthorizeInstantiation({ ADMINISTRADOR, USUARIO, LIDER_DE_PROYECTO, DESARROLLADOR})
public class ItemEditPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3342982114735537862L;

	private ItemEditForm formInput;

	@SuppressWarnings("serial")
	public ItemEditPanel(String id) {
		
		super(id);
		// create a feedback panel
		final Component feedback = new FeedbackPanel("feedback").setOutputMarkupPlaceholderTag(true);
		add(feedback);
		
		add(formInput = new ItemEditForm());
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
				if (getForm().hasError()) {
					
					target.appendJavaScript("alert('" +	getForm().getFeedbackMessages() + "');");
					return;
					
				}
				Item newVersion = (Item) getForm().getModelObject();
				
				Item item;
				try {
					
					if (newVersion.getId() != null) {
						
						item = DataSourceLocator.getInstance().getItemService()
									.getById(newVersion.getId());
						
						item.setTitulo(newVersion.getTitulo());
						item.setDescripcion(newVersion.getDescripcion());
						item.setPrioridad(newVersion.getPrioridad());
						item.setProyecto(newVersion.getProyecto());
						item.setResponsable(newVersion.getResponsable());
						item.setTipoItem(newVersion.getTipoItem());
						item.setTitulo(newVersion.getTitulo());
						
						DataSourceLocator.getInstance().getItemService().update(item);
					} else {
						
						item = newVersion;
						DataSourceLocator.getInstance().getItemService().save(item);
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
		
		final ModalWindow tipoItemEditWindow;
		final TipoItemEditPanel tipoItemEditPanel;
		add(tipoItemEditWindow = new ModalWindow("modal2"));
		
		if (((WicketApplication.WebRoleAuthorizationStrategy)getApplication().getSecuritySettings().getAuthorizationStrategy()).isInstantiationAuthorized(
		   TipoItemEditPanel.class)) {
								
			tipoItemEditWindow.setContent(tipoItemEditPanel = new TipoItemEditPanel(tipoItemEditWindow.getContentId()));
			tipoItemEditWindow.setTitle("Tipo de Item");
			tipoItemEditWindow.setCookieName("modal-2");
		
			tipoItemEditWindow.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

				public boolean onCloseButtonClicked(AjaxRequestTarget target) {
					// setResult("Modal window 2 - close button");
					return true;
				}
			});

			tipoItemEditWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

			/**
			 * 
			 */
			public void onClose(AjaxRequestTarget target) {
				
				target.add(formInput.updateTipoItem());
			}
		});
		
		add(new AjaxLink<Void>("open")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				target.add(tipoItemEditPanel.getFormInput());
				tipoItemEditPanel.getFormInput().clearInput();
				tipoItemEditWindow.show(target);
			}
		});
		} else {
		
			add(new AjaxLink<Void>("open") {
								
				@Override
				public void onClick(AjaxRequestTarget target) {
						
				}

				@Override
				public boolean isVisible() {
					// TODO Auto-generated method stub
					return false;
				}
			});
		}
	}
	
	public void setItemId(Long itemId) {

		Item item = new Item();
		try {
			
			if (itemId != null) {
				item = DataSourceLocator.getInstance().getItemService().getById(itemId);
			}
		} catch (ServiceException e) {
			
		}	

		formInput.setModel(new CompoundPropertyModel<Item>(item));
	}
}
