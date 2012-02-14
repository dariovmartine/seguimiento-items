package edu.unlp.informatica.postgrado.seguimiento.view.historialitem;

import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.ADMINISTRADOR;
import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.LIDER_DE_PROYECTO;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import edu.unlp.informatica.postgrado.seguimiento.WebAuthorizeInstantiation;
import edu.unlp.informatica.postgrado.seguimiento.item.model.HistorialItem;

/**
 * page that demonstrates dataview and sorting
 * 
 * @author dariovmartine
 * 
 */
@WebAuthorizeInstantiation({ ADMINISTRADOR, LIDER_DE_PROYECTO})
public class HistorialItemListadoPanel extends Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6639795032464660258L;
	
	@SpringBean(name="sortableHistorialItemDataProvider")
	HistorialItemSortableDataProvider sortableHistorialItemDataProvider;
			
	private DataView<HistorialItem> dataView = null; 

	/**
	 * constructor
	 */
	@SuppressWarnings("serial")
	public HistorialItemListadoPanel(String id) {
		
		super(id);
		setOutputMarkupId(true);
						
		dataView = new DataView<HistorialItem>("sorting", sortableHistorialItemDataProvider) {
			
			@Override
			protected void populateItem(
					final Item<HistorialItem> item) {
				final HistorialItem historialItemSel = item
						.getModelObject();
				
				item.add(new Label("comentario", historialItemSel.getComentario()));
				item.add(new Label("estado", historialItemSel.getEstado().toString()));
				item.add(new Label("fechaFin", historialItemSel.getFechaFin() != null ? historialItemSel.getFechaFin().toLocaleString() : ""));
				item.add(new Label("fechaInicio", historialItemSel.getFechaInicio().toLocaleString()));
				item.add(new Label("responsable", historialItemSel.getResponsable().toString()));
				item.add(new Label("item", historialItemSel.getItem().toString()));
				
				item.add(AttributeModifier.replace("class",
						new AbstractReadOnlyModel<String>() {

							@Override
							public String getObject() {
								return (item.getIndex() % 2 == 1) ? "even"
										: "odd";
							}
						}));
				
				
			}
		};

		dataView.setItemsPerPage(8);
		
		
		
		add(new OrderByBorder("orderByName", "nombre",
				sortableHistorialItemDataProvider) {

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}
		});		

		add(dataView);

		add(new PagingNavigator("navigator", dataView));
	}
}
