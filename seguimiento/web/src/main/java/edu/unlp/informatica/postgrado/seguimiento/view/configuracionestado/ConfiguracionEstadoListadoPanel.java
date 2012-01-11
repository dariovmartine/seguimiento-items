package edu.unlp.informatica.postgrado.seguimiento.view.configuracionestado;

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
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;
import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;




/**
 * page that demonstrates dataview and sorting
 * 
 * @author dariovmartine
 * 
 */
public class ConfiguracionEstadoListadoPanel extends Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6639795032464660258L;
	
	@SpringBean(name="sortableConfiguracionEstadoDataProvider")
	ConfiguracionEstadoSortableDataProvider sortableConfiguracionEstadoDataProvider;
	
	private ConfiguracionEstadoEditPanel configuracionEstadoEditPanel = null;
	
	private DataView<ConfiguracionEstado> dataView = null; 

	/**
	 * constructor
	 */
	@SuppressWarnings("serial")
	public ConfiguracionEstadoListadoPanel(String id) {
		
		super(id);
		setOutputMarkupId(true);
		final Label result;
		//add(result = new Label("result", new PropertyModel<String>(this, "result")));
		//result.setOutputMarkupId(true);
	
		final ModalWindow configuracionEstadoEditWindow;
		add(configuracionEstadoEditWindow = new ModalWindow("modal2"));
				
		configuracionEstadoEditWindow.setContent(configuracionEstadoEditPanel = new ConfiguracionEstadoEditPanel(configuracionEstadoEditWindow.getContentId()));
		configuracionEstadoEditWindow.setTitle("ConfiguracionItem");
		configuracionEstadoEditWindow.setCookieName("modal-2");
		
		configuracionEstadoEditWindow.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

			public boolean onCloseButtonClicked(AjaxRequestTarget target) {
				// setResult("Modal window 2 - close button");
				return true;
			}
		});

		configuracionEstadoEditWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

			/**
			 * 
			 */
			public void onClose(AjaxRequestTarget target) {
				// target.add(result);
			}
		});
		
		dataView = new DataView<ConfiguracionEstado>("sorting", sortableConfiguracionEstadoDataProvider) {
			
			@Override
			protected void populateItem(final Item<ConfiguracionEstado> item) {
				
				final ConfiguracionEstado configuracionEstadoSel = item.getModelObject();
				
				//item.add(new Label("tipoItem", configuracionEstadoSel.getConfiguracionItem().getProyecto().getTipoItem().getNombre()));
				//item.add(new Label("detalle", configuracionEstadoSel.getProximosEstados().toString()));
				item.add(new Label("proyecto", configuracionEstadoSel.getConfiguracionItem().getProyecto().toString()));
				item.add(new Label("tipoItem", configuracionEstadoSel.getConfiguracionItem().getTipoItem().toString()));
				item.add(new Label("estado", (configuracionEstadoSel.getEstado() == null ? "Sin estado" : configuracionEstadoSel.getEstado().toString())));
				item.add(new Label("estadosPosibles", configuracionEstadoSel.getProximosEstados().toString()));
				
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
						configuracionEstadoEditPanel.setConfiguracionEstadoId(configuracionEstadoSel.getId());
						configuracionEstadoEditWindow.show(target);
					}
				});				
			}
		};

		dataView.setItemsPerPage(8);
		
		
		
		add(new OrderByBorder("orderByTipoItem", "tipoItem",
				sortableConfiguracionEstadoDataProvider) {

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});
		
		add(new OrderByBorder("orderByEstado", "estado",
				sortableConfiguracionEstadoDataProvider) {

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});		

		add(dataView);

		add(new PagingNavigator("navigator", dataView));
	}
}
