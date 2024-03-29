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
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ForeignKey;

import edu.unlp.informatica.postgrado.seguimiento.item.service.MappingOptions;

/**
 * @author  dariovmartine
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "CONFIG_ITEM")
public class ConfiguracionItem  implements Serializable, Numerable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3656444340587170022L;

	@Id 
	@Column(name = "ID")
	@GeneratedValue(generator="CONF_ITEM_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="CONF_ITEM_ID_GEN", sequenceName="SEQ_CONF_ITEM_ID", allocationSize=1, initialValue=1)
	@MappingOptions
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_ITEM")
	@ForeignKey(name="FK_CONF_ITEM_TIPO_ITEM")
	@MappingOptions
	private TipoItem tipoItem;
		
	@OneToMany(cascade={CascadeType.ALL}, orphanRemoval=true)
	@JoinTable(name="CONFIG_ITEM_ESTADO", 
            joinColumns=@JoinColumn(name="CONFIG_ITEM_ID"),
            inverseJoinColumns=@JoinColumn(name="CONFIG_ESTADO_ID"))
    @MapKeyJoinColumn(name="ESTADO_ID")
	@ForeignKey(name="FK_CONF_ITEM_PROX_ESTA")
	@MappingOptions
	Map<Estado, ConfiguracionEstado> proximosEstados = new HashMap<Estado, ConfiguracionEstado>();
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_PROYECTO")
	@ForeignKey(name="FK_CONF_ITEM_PROYECTO")
	@MappingOptions
	private Proyecto proyecto;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	
	
	public Map<Estado, ConfiguracionEstado> getProximosEstados() {
		return proximosEstados;
	}

	public void setProximosEstados(Map<Estado, ConfiguracionEstado> proximosEstados) {
		this.proximosEstados = proximosEstados;
	}
	
	@Transient
	private List<Estado> ret = new ArrayList<Estado>();
	
	public List<Estado> getEstadosIniciales() {

		ret.clear();
		ret.addAll(proximosEstados.keySet());
		return ret;
	}
	
	public void setEstadosIniciales(List<Estado> newEstados) {
		
		for (Estado estado : newEstados) {
		
			if (! proximosEstados.containsKey(estado)) {
				ConfiguracionEstado confEstado = new ConfiguracionEstado();
				confEstado.setEstado(estado);
				confEstado.setConfiguracionItem(this);
				proximosEstados.put(estado, confEstado);
			}
		}		
		
		Object arr[] =  proximosEstados.keySet().toArray();
		for (Object estado : arr) {
		 
			if (! newEstados.contains(estado)) {
				
				proximosEstados.remove(estado);
			}
		}
	}

	public boolean canChangeState(Estado estadoActual, Estado estadoNuevo) {
		
		if (proximosEstados.get(estadoActual) == null) {
			
			return false;
		}
		
		return proximosEstados.get(estadoActual).canChange(estadoNuevo);
	}
	
	public boolean isStateInitial(Estado estadoActual) {
		// TODO Auto-generated method stub
		return proximosEstados.get(estadoActual) != null;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}	

	public TipoItem getTipoItem() {
		return tipoItem;
	}

	public void setTipoItem(TipoItem tipoItem) {
		this.tipoItem = tipoItem;
	}
	
	@Override
	public String toString() {
		String s = "" ; 
		for (Estado estado : proximosEstados.keySet()) {
			s = "Estado: " + estado + " -> " + proximosEstados.get(estado); 
		} 
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((proximosEstados == null) ? 0 : proximosEstados.hashCode());
		result = prime * result
				+ ((tipoItem == null) ? 0 : tipoItem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (! (obj instanceof ConfiguracionItem))
			return false;
		ConfiguracionItem other = (ConfiguracionItem) obj;
		if (proximosEstados == null) {
			if (other.getProximosEstados() != null)
				return false;
		} else if (!proximosEstados.equals(other.getProximosEstados()))
			return false;
		if (tipoItem == null) {
			if (other.getTipoItem() != null)
				return false;
		} else if (!tipoItem.equals(other.getTipoItem()))
			return false;
		return true;
	}

	


}
