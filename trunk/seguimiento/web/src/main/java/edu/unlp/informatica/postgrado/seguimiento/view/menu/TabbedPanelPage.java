package edu.unlp.informatica.postgrado.seguimiento.view.menu;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

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
 * @author ivaynberg
 */
public class TabbedPanelPage extends WebPage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8155537811236517031L;

	/**
	 * Constructor
	 */
	@SuppressWarnings("serial")
	public TabbedPanelPage()
	{
		// create a list of ITab objects used to feed the tabbed panel
		List<ITab> tabs = new ArrayList<ITab>();
		
		tabs.add(new AbstractTab(new Model<String>("Items"))
		{
			@Override
			public Panel getPanel(String panelId)
			{
				return new ItemListadoPanel(panelId);
			}
		});
		
		tabs.add(new AbstractTab(new Model<String>("Proyectos"))
		{
			@Override
			public Panel getPanel(String panelId)
			{
				return new ProyectoListadoPanel(panelId);
			}
		});
		
		tabs.add(new AbstractTab(new Model<String>("Estados"))
		{
			@Override
			public Panel getPanel(String panelId)
			{
				return new EstadoListadoPanel(panelId);
			}
		});

		tabs.add(new AbstractTab(new Model<String>("Tipos de Items"))
		{
			@Override
			public Panel getPanel(String panelId)
			{
				return new TipoItemListadoPanel(panelId);
			}
		});

		tabs.add(new AbstractTab(new Model<String>("Prioridades"))
		{
			@Override
			public Panel getPanel(String panelId)
			{
				return new PrioridadListadoPanel(panelId);
			}
		});
		
		tabs.add(new AbstractTab(new Model<String>("Personas"))
		{
			@Override
			public Panel getPanel(String panelId)
			{
				return new PersonaListadoPanel(panelId);
			}
		});

		tabs.add(new AbstractTab(new Model<String>("Configuracion de Tipos de Items"))
		{
			@Override
			public Panel getPanel(String panelId)
			{
				return new ConfiguracionItemListadoPanel(panelId);
			}
		});
		
		tabs.add(new AbstractTab(new Model<String>("Configuracion de Estados de Items"))
		{
			@Override
			public Panel getPanel(String panelId)
			{
				return new ConfiguracionEstadoListadoPanel(panelId);
			}
		});

		add(new AjaxTabbedPanel("tabs", tabs));
	}
}