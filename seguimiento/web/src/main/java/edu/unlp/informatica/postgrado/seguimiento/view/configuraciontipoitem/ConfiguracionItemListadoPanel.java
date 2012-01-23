package edu.unlp.informatica.postgrado.seguimiento.view.configuraciontipoitem;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
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

import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;

/**
 * page that demonstrates dataview and sorting
 * 
 * @author dariovmartine
 * 
 */
public class ConfiguracionItemListadoPanel extends Panel {
	
	private static final long serialVersionUID = 4716433213674160665L;

	@SpringBean(name="sortableConfiguracionItemDataProvider")
	ConfiguracionItemSortableDataProvider sortableConfiguracionItemDataProvider;
	
	private ConfiguracionItemEditPanel ConfiguracionItemEditPanel = null;
	
	private DataView<ConfiguracionItem> dataView = null; 

	/**
	 * constructor
	 */
	@SuppressWarnings("serial")
	public ConfiguracionItemListadoPanel(String id) {
		
		super(id);
		setOutputMarkupId(true);
		final Label result;
		//add(result = new Label("result", new PropertyModel<String>(this, "result")));
		//result.setOutputMarkupId(true);
	
		final ModalWindow ConfiguracionItemEditWindow;
		add(ConfiguracionItemEditWindow = new ModalWindow("modal2"));
				
		ConfiguracionItemEditWindow.setContent(ConfiguracionItemEditPanel = new ConfiguracionItemEditPanel(ConfiguracionItemEditWindow.getContentId()));
		ConfiguracionItemEditWindow.setTitle("ConfiguracionItem");
		ConfiguracionItemEditWindow.setCookieName("modal-2");
		
		ConfiguracionItemEditWindow.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

			public boolean onCloseButtonClicked(AjaxRequestTarget target) {
				// setResult("Modal window 2 - close button");
				return true;
			}
		});

		ConfiguracionItemEditWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

			/**
			 * 
			 */
			public void onClose(AjaxRequestTarget target) {
				// target.add(result);
			}
		});
		
		dataView = new DataView<ConfiguracionItem>("sorting", sortableConfiguracionItemDataProvider) {
			
			@Override
			protected void populateItem(final Item<ConfiguracionItem> item) {
				
				final ConfiguracionItem configuracionItemSel = item.getModelObject();
								
				item.add(new Label("proyecto", configuracionItemSel.getProyecto().toString()));
				item.add(new Label("tipoItem", configuracionItemSel.getTipoItem().toString()));
				item.add(new Label("estadosIniciales", configuracionItemSel.getProximosEstados().keySet().toString()));
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
						ConfiguracionItemEditPanel.setConfiguracionItemId(configuracionItemSel.getId());
						ConfiguracionItemEditWindow.show(target);
					}
				});				
			}
		};

		dataView.setItemsPerPage(8);
		
		
		
		add(new OrderByBorder("orderByTipoItem", "tipoItem",
				sortableConfiguracionItemDataProvider) {

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});
		
		add(new OrderByBorder("orderByEstado", "estado",
				sortableConfiguracionItemDataProvider) {

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});		

		add(dataView);

		add(new PagingNavigator("navigator", dataView));
	}
}
