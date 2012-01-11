package edu.unlp.informatica.postgrado.seguimiento.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ConfiguracionEstadoService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ConfiguracionItemService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.EstadoService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.HistorialItemService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ItemService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.PersonaService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.PrioridadService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ProyectoService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.TipoItemService;

/**
 * Service locator class for contacts database
 * 
 * @author dariovmartine
 * 
 */
@Service("datasourceLocator")
public class DataSourceLocator
{
	@Autowired
	private ItemService itemService;

	@Autowired
	private EstadoService estadoService;

	@Autowired
	private TipoItemService tipoItemService;

	@Autowired
	private PrioridadService prioridadService;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private ProyectoService proyectoService;
	
	@Autowired
	private ConfiguracionEstadoService configuracionEstadoService;
	
	@Autowired
	private HistorialItemService historialItemService;

	private static DataSourceLocator dataSource;
	
	public DataSourceLocator() {
		super();
		dataSource = this;
	}
	/**
	 * @return contacts database
	 */
	public static DataSourceLocator getInstance()
	{
		try {
			if (dataSource.getEstadoService().find().size() == 0) {
				
				Persona i = new Persona();
				i.setNombre("Jefe");

				dataSource.getPersonaService().save(i);
							
				TipoItem ti = new TipoItem();
				ti.setNombre("Ampliació2");
				dataSource.getTipoItemService().save(ti);
				
				Estado e = new Estado();
				e.setNombre("Inicial");
				dataSource.getEstadoService().save(e);
				
				Estado e2 = new Estado();
				e2.setNombre("Finalizado");
				dataSource.getEstadoService().save(e2);
				
				ConfiguracionItem ci = new ConfiguracionItem();
				ConfiguracionEstado confEstado = new ConfiguracionEstado();
				confEstado.setConfiguracionItem(ci);
				confEstado.getProximosEstados().add(e2);
				ci.getProximosEstados().put(e, confEstado);
				
				Proyecto p = new Proyecto();
				p.setLider(i);
				p.setNombre("ppp");
				p.getTipoItems().put(ti, ci);
				ci.setProyecto(p);
				ci.setTipoItem(ti);
				dataSource.getProyectoService().save(p);
				
				
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataSource;
	}
	
	/**
	 * @return the itemService
	 */
	public ItemService getItemService() {
		return itemService;
	}

	/**
	 * @param itemService the itemService to set
	 */
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	/**
	 * @return the estadoService
	 */
	public EstadoService getEstadoService() {
		return estadoService;
	}

	/**
	 * @param estadoService the estadoService to set
	 */
	public void setEstadoService(EstadoService estadoService) {
		this.estadoService = estadoService;
	}
	
	public TipoItemService getTipoItemService() {
		return tipoItemService;
	}
	
	public void setTipoItemService(TipoItemService tipoItemService) {
		this.tipoItemService = tipoItemService;
	}
	
	public PrioridadService getPrioridadService() {
	
		return prioridadService;
	}
	
	public void setPrioridadService(PrioridadService prioridadService) {
		this.prioridadService = prioridadService;
	}
	
	public PersonaService getPersonaService() {
		return personaService;
	}
	
	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}
	
	public ProyectoService getProyectoService() {
		return proyectoService;
	}
	
	public void setProyectoService(ProyectoService proyectoService) {
		this.proyectoService = proyectoService;
	}
	
	public ConfiguracionEstadoService getConfiguracionEstadoService() {
		return configuracionEstadoService;
	}
	
	public void setConfiguracionEstadoService(ConfiguracionEstadoService configuracionEstadoService) {
		this.configuracionEstadoService = configuracionEstadoService;
	}
	
	public HistorialItemService getHistorialItemService() {
		return historialItemService;
	}
	
	public void setHistorialItemService(HistorialItemService historialItemService) {
		this.historialItemService = historialItemService;
	}

}
