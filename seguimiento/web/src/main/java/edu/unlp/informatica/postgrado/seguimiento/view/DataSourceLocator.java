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
import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
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
			if (dataSource.getPersonaService().find().size() == 0) {
				
				Persona admin = new Persona();
				admin.setNombre("Administrador");
				admin.setHabilitado(true);
				admin.setUserName("admin");
				admin.setPassword("admin");
				admin.getRoles().add(ADMINISTRADOR);
				dataSource.getPersonaService().save(admin);
				
				Persona lider = new Persona();
				lider.setNombre("Lider de proyecto");
				lider.setHabilitado(true);
				lider.setUserName("lider");
				lider.setPassword("lider");
				lider.getRoles().add(LIDER_DE_PROYECTO);
				dataSource.getPersonaService().save(lider);
				
				Persona i = new Persona();
				i.setNombre("Desarrollador");
				i.setHabilitado(true);
				i.setUserName("desa");
				i.setPassword("desa");
				i.getRoles().add(DESARROLLADOR);
				dataSource.getPersonaService().save(i);
				
				i = new Persona();
				i.setNombre("Usuario");
				i.setHabilitado(true);
				i.setUserName("user");
				i.setPassword("user");
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
								
				Estado eAceptado = new Estado();
				eAceptado.setNombre("Aceptado");
				eAceptado.setTipoEstado(INICIAL); 
				dataSource.getEstadoService().save(eAceptado);

				Estado eCreado = new Estado();
				eCreado.setNombre("Creado");
				eCreado.setTipoEstado(INICIAL); 
				dataSource.getEstadoService().save(eCreado);
				
				Estado eDesarrollo = new Estado();
				eDesarrollo.setNombre("Desarrollo");
				eDesarrollo.setTipoEstado(INTERMEDIO);
				dataSource.getEstadoService().save(eDesarrollo);
				
				Estado eValidacion = new Estado();
				eValidacion.setNombre("Validación");
				eValidacion.setTipoEstado(INTERMEDIO);
				dataSource.getEstadoService().save(eValidacion);
				
				Estado eTerminado = new Estado();
				eTerminado.setNombre("Terminado");
				eTerminado.setTipoEstado(FINAL);
				dataSource.getEstadoService().save(eTerminado);
				
				ConfiguracionItem ci = new ConfiguracionItem();
				ConfiguracionEstado confEstado = new ConfiguracionEstado();
				confEstado.setConfiguracionItem(ci);
				confEstado.setEstado(eCreado);
				confEstado.getProximosEstados().add(eAceptado);
				ci.getProximosEstados().put(eCreado, confEstado);
				
				ConfiguracionEstado confEstado2 = new ConfiguracionEstado();
				confEstado2.setConfiguracionItem(ci);
				confEstado2.setEstado(eAceptado);
				confEstado2.getProximosEstados().add(eDesarrollo);
				ci.getProximosEstados().put(eAceptado, confEstado2);
				
				ConfiguracionEstado confEstado3 = new ConfiguracionEstado();
				confEstado3.setConfiguracionItem(ci);
				confEstado3.setEstado(eDesarrollo);
				confEstado3.getProximosEstados().add(eTerminado);
				ci.getProximosEstados().put(eDesarrollo, confEstado3);
				
				ConfiguracionItem ci2 = new ConfiguracionItem();
				ConfiguracionEstado ce2 = new ConfiguracionEstado();
				ce2.setConfiguracionItem(ci2);
				ce2.setEstado(eAceptado);
				ce2.getProximosEstados().add(eDesarrollo);
				ci2.getProximosEstados().put(eAceptado, ce2);
				
				confEstado2 = new ConfiguracionEstado();
				confEstado2.setConfiguracionItem(ci2);
				confEstado2.setEstado(eAceptado);
				confEstado2.getProximosEstados().add(eDesarrollo);
				ci2.getProximosEstados().put(eAceptado, confEstado2);
				
				confEstado3 = new ConfiguracionEstado();
				confEstado3.setConfiguracionItem(ci2);
				confEstado3.setEstado(eDesarrollo);
				confEstado3.getProximosEstados().add(eTerminado);
				ci2.getProximosEstados().put(eDesarrollo, confEstado3);
				
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
				
				Item ii = new Item();
				ii.setDescripcion("sss");
				ii.setPrioridad(pr);
				ii.setResponsable(lider);
				ii.setTitulo("ss");
				ii.setTipoItem(ti);
				ii.setEstado(eCreado);
				ii.setProyecto(p);
				dataSource.getItemService().save(ii);
				
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
