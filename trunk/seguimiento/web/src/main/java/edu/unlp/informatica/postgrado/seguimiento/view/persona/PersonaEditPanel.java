package edu.unlp.informatica.postgrado.seguimiento.view.persona;

import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.ADMINISTRADOR;

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
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

@WebAuthorizeInstantiation({ ADMINISTRADOR})
public class PersonaEditPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3342982114735537862L;

	private PersonaEditForm formInput;

	@SuppressWarnings("serial")
	public PersonaEditPanel(String id) {
		
		super(id);
		// create a feedback panel
		final Component feedback = new FeedbackPanel("feedback").setOutputMarkupPlaceholderTag(true);
		add(feedback);
		
		add(formInput = new PersonaEditForm());
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
				Persona newVersion = (Persona) getForm().getModelObject();
				
				Persona persona;
				try {
					
					if (newVersion.getId() != null) {
						
						persona = DataSourceLocator.getInstance().getPersonaService().getById(newVersion.getId());
						
						DataSourceLocator.getInstance().getPersonaService().update(newVersion);
					} else {
						
						persona = newVersion;
						DataSourceLocator.getInstance().getPersonaService().save(persona);
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
	
	public void setPersonaId(Long personaId) {

		Persona persona = new Persona();
		try {
			
			if (personaId != null) {
				persona = DataSourceLocator.getInstance().getPersonaService().getById(personaId);
			}
		} catch (ServiceException e) {
			
		}	

		formInput.setModel(new CompoundPropertyModel<Persona>(persona));
	}
}
