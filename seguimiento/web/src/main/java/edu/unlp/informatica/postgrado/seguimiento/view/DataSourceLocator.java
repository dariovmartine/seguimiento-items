package edu.unlp.informatica.postgrado.seguimiento.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unlp.informatica.postgrado.seguimiento.item.service.ConfiguracionItemService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.EstadoService;
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
	private ConfiguracionItemService configuracionItemService;

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
	public ConfiguracionItemService getConfiguracionItemService() {
		return configuracionItemService;
	}
	public void setConfiguracionItemService(
			ConfiguracionItemService configuracionItemService) {
		this.configuracionItemService = configuracionItemService;
	}
}
