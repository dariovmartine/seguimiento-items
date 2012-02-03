package edu.unlp.informatica.postgrado.seguimiento.view.item;

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
import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.*;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;




/**
 * page that demonstrates dataview and sorting
 * 
 * @author dariovmartine
 * 
 */
@WebAuthorizeInstantiation({ ROLE_USER , ROLE_SUPERVISOR})
public class ItemListadoPanel extends Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6639795032464660258L;
	
	@SpringBean(name="sortableItemDataProvider")
	ItemSortableDataProvider sortableItemDataProvider;
	
	private ItemEditPanel itemEditPanel = null;
	
	private DataView<edu.unlp.informatica.postgrado.seguimiento.item.model.Item> dataView = null; 

	/**
	 * constructor
	 */
	@SuppressWarnings("serial")
	public ItemListadoPanel(String id) {
		
		super(id);
		setOutputMarkupId(true);
			
		final ModalWindow itemEditWindow;
		add(itemEditWindow = new ModalWindow("modal2"));
				
		itemEditWindow.setContent(itemEditPanel = new ItemEditPanel(itemEditWindow.getContentId()));
		itemEditWindow.setTitle("Item");
		itemEditWindow.setCookieName("modal-2");
		
		itemEditWindow.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

			public boolean onCloseButtonClicked(AjaxRequestTarget target) {
				// setResult("Modal window 2 - close button");
				return true;
			}
		});

		itemEditWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

			/**
			 * 
			 */
			public void onClose(AjaxRequestTarget target) {
				// target.add(result);
			}
		});
		
		dataView = new DataView<edu.unlp.informatica.postgrado.seguimiento.item.model.Item>("sorting", sortableItemDataProvider) {
			
			@Override
			protected void populateItem(
					final Item<edu.unlp.informatica.postgrado.seguimiento.item.model.Item> item) {
				final edu.unlp.informatica.postgrado.seguimiento.item.model.Item itemSel = item
						.getModelObject();
				
				item.add(new Label("titulo", itemSel.getTitulo()));
				item.add(new Label("estado", itemSel.getEstado().toString())); 

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
						itemEditPanel.setItemId(itemSel.getId());
						itemEditWindow.show(target);
					}
				});				
		        
				item.add(new AjaxLink<Void>("doDelete") {					

					@Override
					public void onClick(AjaxRequestTarget target) {
						try {
							DataSourceLocator.getInstance().getItemService().delete(itemSel);
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
								return "if(confirm('Está seguro que quiere eliminar: " + itemSel.getTitulo()  + "?')) {" + script + "}" ;
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
				itemEditPanel.setItemId(null);
				itemEditWindow.show(target);
			}
		});
		
		add(new OrderByBorder("orderByTitulo", "titulo",
				sortableItemDataProvider) {

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});

		add(new OrderByBorder("orderByState", "estado",
				sortableItemDataProvider) {

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});

		add(dataView);

		add(new PagingNavigator("navigator", dataView));
	}
}
