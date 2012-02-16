package edu.unlp.informatica.postgrado.seguimiento.item.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ConfiguracionEstadoService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ConfiguracionItemService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.MappingOptions;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ProyectoService;

/**
 * @author dariovmartine
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "PROYECTO")
public class Proyecto implements Serializable, Numerable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6774599877348543348L;

	@Id 
	@Column(name = "ID")
	@GeneratedValue(generator="PROYECTO_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="PROYECTO_ID_GEN", sequenceName="SEQ_PROYECTO_ID", allocationSize=1, initialValue=1)
	@MappingOptions
	Long id;
	
	@NotNull
	@Column(name = "NOMBRE", unique=true)
	@MappingOptions
	String nombre;
	
	@ManyToMany(targetEntity=Persona.class)
    @JoinTable(name="INTEGRANTES",
    		joinColumns=@JoinColumn(name="PROYECTO_ID"),
	        inverseJoinColumns=@JoinColumn(name="PERSONA_ID"))
    @MappingOptions(exclude={ConfiguracionEstadoService.class, ConfiguracionItemService.class})
	List<Persona> integrantes = new ArrayList<Persona>();
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_LIDER")
	@MappingOptions(exclude={ConfiguracionEstadoService.class, ConfiguracionItemService.class})	
	Persona lider;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)	
	@JoinTable(name="PROYECTO_CONF",
            joinColumns=@JoinColumn(name="PROYECTO_ID"),
            inverseJoinColumns=@JoinColumn(name="CONFIG_ITEM_ID"))
    @MapKeyJoinColumn(name="TIPO_ITEM_ID")
	@MappingOptions(exclude={ConfiguracionEstadoService.class, ConfiguracionItemService.class})
    Map<TipoItem, ConfiguracionItem> tipoItems = new HashMap<TipoItem, ConfiguracionItem>();

    @OneToMany(mappedBy="proyecto")
    @MappingOptions(exclude={ConfiguracionEstadoService.class, ConfiguracionItemService.class, ProyectoService.class})
	List<Item> items = new ArrayList<Item>();
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Persona> getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(List<Persona> integrantes) {
	
		if (items != null) {
			for (Item item : items) {
				if (! integrantes.contains(item.getResponsable()) && ! integrantes.contains(getLider()) ) {
					throw new IllegalArgumentException(item.getResponsable()  + " no puede eliminarse porque es responsable del item " 
						+ item );		
				}
			}
		}
		this.integrantes = integrantes;
	}

	public Persona getLider() {
		return lider;
	}

	public void setLider(Persona lider) {
		if (! lider.getRoles().contains(Rol.LIDER_DE_PROYECTO)) {
			throw new IllegalArgumentException("La persona no tiene el rol de lider de proyecto.");
		}
		this.lider = lider;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Map<TipoItem, ConfiguracionItem> getTipoItems() {
		return tipoItems;
	}

	public void setTipoItems(Map<TipoItem,ConfiguracionItem> tipoItems) {
		this.tipoItems = tipoItems;
	}
	
	public boolean canAddItem(Item item) {
		if (item != null && item.getTipoItem() != null) {
			return tipoItems.containsKey(item.getTipoItem());
		}
		return true;
	}
	
	public boolean canUseTipoItem(TipoItem tipoItem) {
		if (tipoItem != null) {
			return tipoItems.containsKey(tipoItem);
		}
		return true;
	}
	
	public boolean isStateInitial(TipoItem tipoItem, Estado estadoActual) {
		
	if (tipoItems == null) {
			
			return true;
		}
		
		if (tipoItems.get(tipoItem) == null) {
			
			return false;
		}
		
		if (tipoItems.get(tipoItem).getProximosEstados() == null) {
			
			return false;
		}		
			
		return tipoItems.get(tipoItem).isStateInitial(estadoActual);
	}
	
	public boolean canChangeState(TipoItem tipoItem, Estado estadoActual, Estado estadoNuevo) {

		if (tipoItems == null) {
			
			return true;
		}
		
		if (tipoItems.get(tipoItem) == null) {
			
			return false;
		}
		
		if (tipoItems.get(tipoItem).getProximosEstados() == null) {
			
			return false;
		}		
			
		return tipoItems.get(tipoItem).canChangeState(estadoActual, estadoNuevo);
	}
	
	public boolean isPersonInPoject(Persona persona){
		if (persona != null) {
			return this.getIntegrantes().contains(persona) || 
				persona.equals(lider);
		}
		return false;
	}
	
	@Transient
	private List<TipoItem> ret = new ArrayList<TipoItem>();
	
	public List<TipoItem> getTipoItemList() {

		ret.clear();
		ret.addAll(tipoItems.keySet());
		return ret;
	}
	
	public void setTipoItemList(List<TipoItem> newTipoItems) {
		
		for (TipoItem tipoItem : newTipoItems) {
			
			if (! tipoItems.containsKey(tipoItem)) {
				ConfiguracionItem confItem = new ConfiguracionItem();
				confItem.setProyecto(this);
				confItem.setTipoItem(tipoItem);
				tipoItems.put(tipoItem, confItem);
			}
		}		
		
		Object arr[] =  tipoItems.keySet().toArray();
		for (Object tipoItem : arr) {
		 
			if (! newTipoItems.contains(tipoItem)) {
				
				tipoItems.remove(tipoItem);
			}
		}
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((integrantes == null) ? 0 : integrantes.hashCode());
		result = prime * result + ((lider == null) ? 0 : lider.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((tipoItems == null) ? 0 : tipoItems.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Proyecto))
			return false;
		Proyecto other = (Proyecto) obj;
		if (integrantes == null) {
			if (other.getIntegrantes() != null)
				return false;
		} else if (!integrantes.equals(other.getIntegrantes()))
			return false;
		if (lider == null) {
			if (other.getLider() != null)
				return false;
		} else if (!lider.equals(other.getLider()))
			return false;
		if (nombre == null) {
			if (other.getNombre() != null)
				return false;
		} else if (!nombre.equals(other.getNombre()))
			return false;
		if (tipoItems == null) {
			if (other.getTipoItems() != null)
				return false;
		} else if (!tipoItems.equals(other.getTipoItems()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nombre;
	}

	
	
	
}
