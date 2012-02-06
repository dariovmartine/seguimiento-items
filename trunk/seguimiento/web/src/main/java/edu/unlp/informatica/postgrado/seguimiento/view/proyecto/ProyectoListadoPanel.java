package edu.unlp.informatica.postgrado.seguimiento.view.proyecto;

import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.ADMINISTRADOR;
import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.LIDER_DE_PROYECTO;

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
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;

/**
 * page that demonstrates dataview and sorting
 * 
 * @author dariovmartine
 * 
 */
@WebAuthorizeInstantiation({ ADMINISTRADOR, LIDER_DE_PROYECTO })
public class ProyectoListadoPanel extends Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6639795032464660258L;
	
	@SpringBean(name="sortableProyectoDataProvider")
	ProyectoSortableDataProvider sortableProyectoDataProvider;
	
	private ProyectoEditPanel proyectoEditPanel = null;
	
	private DataView<Proyecto> dataView = null; 

	/**
	 * constructor
	 */
	@SuppressWarnings("serial")
	public ProyectoListadoPanel(String id) {
		
		super(id);
		setOutputMarkupId(true);
			
		final ModalWindow proyectoEditWindow;
		add(proyectoEditWindow = new ModalWindow("modal2"));
				
		proyectoEditWindow.setContent(proyectoEditPanel = new ProyectoEditPanel(proyectoEditWindow.getContentId()));
		proyectoEditWindow.setTitle("Proyecto");
		proyectoEditWindow.setCookieName("modal-2");
		
		proyectoEditWindow.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

			public boolean onCloseButtonClicked(AjaxRequestTarget target) {
				// setResult("Modal window 2 - close button");
				return true;
			}
		});

		proyectoEditWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

			/**
			 * 
			 */
			public void onClose(AjaxRequestTarget target) {
				// target.add(result);
			}
		});
		
		dataView = new DataView<Proyecto>("sorting", sortableProyectoDataProvider) {
			
			@Override
			protected void populateItem(
					final Item<Proyecto> item) {
				final Proyecto proyectoSel = item
						.getModelObject();
				
				item.add(new Label("nombre", proyectoSel.getNombre()));
				
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
						proyectoEditPanel.setProyectoId(proyectoSel.getId());
						proyectoEditWindow.show(target);
					}
				});				
		        
				item.add(new AjaxLink<Void>("doDelete") {					

					@Override
					public void onClick(AjaxRequestTarget target) {
						try {
							DataSourceLocator.getInstance().getProyectoService().delete(proyectoSel);
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
								return "if(confirm('Está seguro que quiere eliminar: " + proyectoSel.getNombre()  + "?')) {" + script + "}" ;
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
				proyectoEditPanel.setProyectoId(null);
				proyectoEditWindow.show(target);
			}
		});
		
		add(new OrderByBorder("orderByName", "nombre",
				sortableProyectoDataProvider) {

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});		

		add(dataView);

		add(new PagingNavigator("navigator", dataView));
	}
}
