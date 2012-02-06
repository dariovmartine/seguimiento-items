package edu.unlp.informatica.postgrado.seguimiento.view.persona;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import edu.unlp.informatica.postgrado.seguimiento.WebAuthorizeInstantiation;
import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;
import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.ADMINISTRADOR;

/**
 * page that demonstrates dataview and sorting
 * 
 * @author dariovmartine
 * 
 */
@WebAuthorizeInstantiation({ ADMINISTRADOR })
public class PersonaListadoPanel extends Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6639795032464660258L;
	
	@SpringBean(name="sortablePersonaDataProvider")
	PersonaSortableDataProvider sortablePersonaDataProvider;
	
	private PersonaEditPanel personaEditPanel = null;
	
	private DataView<Persona> dataView = null; 

	/**
	 * constructor
	 */
	@SuppressWarnings("serial")
	public PersonaListadoPanel(String id) {
		
		super(id);
		setOutputMarkupId(true);
		
		//add(result = new Label("result", new PropertyModel<String>(this, "result")));
		//result.setOutputMarkupId(true);
	
		final ModalWindow personaEditWindow;
		add(personaEditWindow = new ModalWindow("modal2"));
				
		personaEditWindow.setContent(personaEditPanel = new PersonaEditPanel(personaEditWindow.getContentId()));
		personaEditWindow.setTitle("Persona");
		personaEditWindow.setCookieName("modal-2");
		
		personaEditWindow.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

			public boolean onCloseButtonClicked(AjaxRequestTarget target) {
				// setResult("Modal window 2 - close button");
				return true;
			}
		});

		personaEditWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

			/**
			 * 
			 */
			public void onClose(AjaxRequestTarget target) {
				// target.add(result);
			}
		});
		
		dataView = new DataView<Persona>("sorting", sortablePersonaDataProvider) {
			
			@Override
			protected void populateItem(
					final Item<Persona> item) {
				final Persona personaSel = item
						.getModelObject();
				
				item.add(new Label("nombre", personaSel.getNombre()));
				
				item.add(AttributeModifier.replace("class",
						new AbstractReadOnlyModel<String>() {

							@Override
							public String getObject() {
								return (item.getIndex() % 2 == 1) ? "even"
										: "odd";
							}
						}));
				
				item.add(new AjaxLink<Void>("doEdit") {
					
					@Override
					public void onClick(AjaxRequestTarget target) {
						personaEditPanel.setPersonaId(personaSel.getId());
						personaEditWindow.show(target);
					}
				});				
		        
				item.add(new AjaxLink<Void>("doDelete") {					

					@Override
					public void onClick(AjaxRequestTarget target) {
						try {
							DataSourceLocator.getInstance().getPersonaService().delete(personaSel);
							target.add(this.getParent().getParent().getParent());
						} catch (ServiceException e) {
							target.appendJavaScript("alert('" +	e.getCause().getCause().getCause().getLocalizedMessage() + "');");
						}
					}

					@Override
					protected IAjaxCallDecorator getAjaxCallDecorator() {
						return new AjaxCallDecorator()
						{
							public CharSequence decorateScript(Component c, CharSequence script)
							{
								return "if(confirm('Está seguro que quiere eliminar: " + personaSel.getNombre()  + "?')) {" + script + "}" ;
							}
						};
					}
				});
			}
		};

		dataView.setItemsPerPage(8);
		
		add(new AjaxLink<Void>("doAdd") {
			
			@Override
			public void onClick(AjaxRequestTarget target) {
				personaEditPanel.setPersonaId(null);
				personaEditWindow.show(target);
			}
		});
		
		add(new OrderByBorder("orderByName", "nombre",
				sortablePersonaDataProvider) {

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});		

		add(dataView);

		add(new PagingNavigator("navigator", dataView));
	}
}
