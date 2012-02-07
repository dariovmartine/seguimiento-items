package edu.unlp.informatica.postgrado.seguimiento.view;

import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.ADMINISTRADOR;
import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.DESARROLLADOR;
import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.LIDER_DE_PROYECTO;
import static edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol.USUARIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Prioridad;
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
import static edu.unlp.informatica.postgrado.seguimiento.item.model.TipoEstado.*;

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
	private ConfiguracionItemService configuracionItemService;
		
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
				
				Persona admin = new Persona();
				admin.setNombre("Jefe");
				admin.setHabilitado(true);
				admin.setUserName("test");
				admin.setPassword("test");
				admin.getRoles().add(ADMINISTRADOR);
				dataSource.getPersonaService().save(admin);
				
				Persona lider = new Persona();
				lider.setNombre("Lujan");
				lider.setHabilitado(true);
				lider.setUserName("lu");
				lider.setPassword("lu");
				lider.getRoles().add(LIDER_DE_PROYECTO);
				dataSource.getPersonaService().save(lider);
				
				Persona i = new Persona();
				i.setNombre("Dario");				
				i.setHabilitado(true);
				i.setUserName("dario");
				i.setPassword("dario");
				i.getRoles().add(DESARROLLADOR);
				dataSource.getPersonaService().save(i);
				
				i = new Persona();
				i.setNombre("Juan");
				i.setHabilitado(true);
				i.setUserName("juan");
				i.setPassword("juan");
				i.getRoles().add(USUARIO);
				dataSource.getPersonaService().save(i);
				
				Prioridad pr = new Prioridad();
				pr.setNombre("ALTA");
				dataSource.getPrioridadService().save(pr);
				pr = new Prioridad();
				pr.setNombre("BAJA");
				dataSource.getPrioridadService().save(pr);
				pr = new Prioridad();
				pr.setNombre("MEDIA");
				dataSource.getPrioridadService().save(pr);
							
				TipoItem ti = new TipoItem();
				ti.setNombre("Ampliación");
				dataSource.getTipoItemService().save(ti);
				
				ti = new TipoItem();
				ti.setNombre("Reporte de bug");
				dataSource.getTipoItemService().save(ti);
				
				ti = new TipoItem();
				ti.setNombre("Mejora");
				dataSource.getTipoItemService().save(ti);
				
				TipoItem ti2 = new TipoItem();
				ti2.setNombre("Nuevo requerimiento");
				dataSource.getTipoItemService().save(ti2);
								
				Estado e = new Estado();
				e.setNombre("Aceptado");
				e.setTipoEstado(INICIAL); 
				dataSource.getEstadoService().save(e);

				e = new Estado();
				e.setNombre("Creado");
				e.setTipoEstado(INICIAL); 
				dataSource.getEstadoService().save(e);
				
				Estado e2 = new Estado();
				e2.setNombre("Desarrollo");
				e2.setTipoEstado(INTERMEDIO);
				dataSource.getEstadoService().save(e2);
				
				e2 = new Estado();
				e2.setNombre("Validación");
				e2.setTipoEstado(INTERMEDIO);
				dataSource.getEstadoService().save(e2);
				
				e2 = new Estado();
				e2.setNombre("Terminado");
				e2.setTipoEstado(FINAL);
				dataSource.getEstadoService().save(e2);
				
				ConfiguracionItem ci = new ConfiguracionItem();
				ConfiguracionEstado confEstado = new ConfiguracionEstado();
				confEstado.setConfiguracionItem(ci);
				confEstado.setEstado(e);
				confEstado.getProximosEstados().add(e2);
				ci.getProximosEstados().put(e, confEstado);
				
				ConfiguracionItem ci2 = new ConfiguracionItem();
				ConfiguracionEstado ce2 = new ConfiguracionEstado();
				ce2.setConfiguracionItem(ci2);
				ce2.setEstado(e);
				ce2.getProximosEstados().add(e2);
				ci2.getProximosEstados().put(e, ce2);
				
				Proyecto p = new Proyecto();
				p.setLider(lider);
				p.getIntegrantes().add(i);
				p.setNombre("Seguimiento de Items");
				p.getTipoItems().put(ti, ci);
				ci.setProyecto(p);
				ci.setTipoItem(ti);
				p.getTipoItems().put(ti2, ci2);
				ci2.setProyecto(p);
				ci2.setTipoItem(ti2);
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
	
	public ConfiguracionItemService getConfiguracionItemService() {
		return configuracionItemService;
	}
	
	public void setConfiguracionItemService(
			ConfiguracionItemService configuracionItemService) {
		this.configuracionItemService = configuracionItemService;
	}
}
