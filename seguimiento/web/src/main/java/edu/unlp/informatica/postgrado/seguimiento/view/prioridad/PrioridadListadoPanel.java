package edu.unlp.informatica.postgrado.seguimiento.view.prioridad;

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

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Prioridad;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;




/**
 * page that demonstrates dataview and sorting
 * 
 * @author dariovmartine
 * 
 */
public class PrioridadListadoPanel extends Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6639795032464660258L;
	
	@SpringBean(name="sortablePrioridadDataProvider")
	PrioridadSortableDataProvider sortablePrioridadDataProvider;
	
	private PrioridadEditPanel prioridadEditPanel = null;
	
	private DataView<Prioridad> dataView = null; 

	/**
	 * constructor
	 */
	@SuppressWarnings("serial")
	public PrioridadListadoPanel(String id) {
		
		super(id);
		setOutputMarkupId(true);
		final Label result;
		//add(result = new Label("result", new PropertyModel<String>(this, "result")));
		//result.setOutputMarkupId(true);
	
		final ModalWindow prioridadEditWindow;
		add(prioridadEditWindow = new ModalWindow("modal2"));
				
		prioridadEditWindow.setContent(prioridadEditPanel = new PrioridadEditPanel(prioridadEditWindow.getContentId()));
		prioridadEditWindow.setTitle("Prioridad");
		prioridadEditWindow.setCookieName("modal-2");
		
		prioridadEditWindow.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

			public boolean onCloseButtonClicked(AjaxRequestTarget target) {
				// setResult("Modal window 2 - close button");
				return true;
			}
		});

		prioridadEditWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

			/**
			 * 
			 */
			public void onClose(AjaxRequestTarget target) {
				// target.add(result);
			}
		});
		
		dataView = new DataView<Prioridad>("sorting", sortablePrioridadDataProvider) {
			
			@Override
			protected void populateItem(
					final Item<Prioridad> item) {
				final Prioridad prioridadSel = item
						.getModelObject();
				
				item.add(new Label("nombre", prioridadSel.getNombre()));
				
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
						prioridadEditPanel.setPrioridadId(prioridadSel.getId());
						prioridadEditWindow.show(target);
					}
				});				
		        
				item.add(new AjaxLink<Void>("doDelete") {					

					@Override
					public void onClick(AjaxRequestTarget target) {
						try {
							DataSourceLocator.getInstance().getPrioridadService().delete(prioridadSel);
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
								return "if(confirm('Está seguro que quiere eliminar: " + prioridadSel.getNombre()  + "?')) {" + script + "}" ;
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
				prioridadEditPanel.setPrioridadId(null);
				prioridadEditWindow.show(target);
			}
		});
		
		add(new OrderByBorder("orderByName", "nombre",
				sortablePrioridadDataProvider) {

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});		

		add(dataView);

		add(new PagingNavigator("navigator", dataView));
	}
}
