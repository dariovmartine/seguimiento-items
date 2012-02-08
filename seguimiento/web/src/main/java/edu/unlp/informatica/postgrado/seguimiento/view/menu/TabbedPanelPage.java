package edu.unlp.informatica.postgrado.seguimiento.view.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import edu.unlp.informatica.postgrado.seguimiento.HeaderPage;
import edu.unlp.informatica.postgrado.seguimiento.WebAuthorizeInstantiation;
import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.*;
import edu.unlp.informatica.postgrado.seguimiento.view.configuracionestado.ConfiguracionEstadoListadoPanel;
import edu.unlp.informatica.postgrado.seguimiento.view.configuraciontipoitem.ConfiguracionItemListadoPanel;
import edu.unlp.informatica.postgrado.seguimiento.view.estado.EstadoListadoPanel;
import edu.unlp.informatica.postgrado.seguimiento.view.item.ItemListadoPanel;
import edu.unlp.informatica.postgrado.seguimiento.view.persona.PersonaListadoPanel;
import edu.unlp.informatica.postgrado.seguimiento.view.prioridad.PrioridadListadoPanel;
import edu.unlp.informatica.postgrado.seguimiento.view.proyecto.ProyectoListadoPanel;
import edu.unlp.informatica.postgrado.seguimiento.view.tipoitem.TipoItemListadoPanel;

/**
 * Tabbed panel demo.
 * 
 * @author dariovmartine
 */
@WebAuthorizeInstantiation({ ADMINISTRADOR, USUARIO, LIDER_DE_PROYECTO, DESARROLLADOR})
public class TabbedPanelPage extends WebPage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8155537811236517031L;

	private final Map<String, Class<? extends Panel>>  panels = new HashMap<String, Class<? extends Panel>>();
	
	/**
	 * Constructor
	 */
	@SuppressWarnings("serial")
	public TabbedPanelPage()
	{
		// create a list of ITab objects used to feed the tabbed panel
		List<ITab> tabs = new ArrayList<ITab>();
		add(new HeaderPage("mainNavigation","Seguimiento de items", this));
		
		panels.put("Items", ItemListadoPanel.class);
		panels.put("Proyectos", ProyectoListadoPanel.class);
		panels.put("Estados", EstadoListadoPanel.class);
		panels.put("Tipos de Items", TipoItemListadoPanel.class);
		panels.put("Prioridades", PrioridadListadoPanel.class);
		panels.put("Personas", PersonaListadoPanel.class);
		panels.put("Configuracion de Tipos de Items", ConfiguracionItemListadoPanel.class);
		panels.put("Configuracion de Estados de Items", ConfiguracionEstadoListadoPanel.class);
		
		for (String title : panels.keySet()) {
		
			final Class<? extends Panel> panel = panels.get(title);
			if (getApplication().getSecuritySettings().getAuthorizationStrategy().isInstantiationAuthorized(panel)) {
				tabs.add(new AbstractTab(new Model<String>(title)) {
					
					@Override
					public Panel getPanel(String panelId) {
						
						try {
							
							return panel.getConstructor(String.class).newInstance(panelId);
						} catch (Exception e) {
							
							e.printStackTrace();
						} 
						return null;
					}
				});
			}
		}

		add(new AjaxTabbedPanel("tabs", tabs));
	}
}