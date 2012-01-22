package edu.unlp.informatica.postgrado.seguimiento.item.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ForeignKey;

/**
 * @author  Victor.Martinez
 */
@Entity
@Table(name = "CONFIG_ITEM")
public class ConfiguracionItem  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3656444340587170022L;

	@Id 
	@Column(name = "ID")
	@GeneratedValue(generator="CONF_ITEM_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="CONF_ITEM_ID_GEN", sequenceName="SEQ_CONF_ITEM_ID", allocationSize=1, initialValue=1)
	private Long id;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_TIPO_ITEM")
	@ForeignKey(name="FK_CONF_ITEM_TIPO_ITEM")
	private TipoItem tipoItem;
		
	@OneToMany(cascade={CascadeType.ALL})
	@JoinTable(name="CONFIG_ITEM_ESTADO", 
            joinColumns=@JoinColumn(name="CONFIG_ITEM_ID"),
            inverseJoinColumns=@JoinColumn(name="CONFIG_ESTADO_ID"))
    @MapKeyJoinColumn(name="ESTADO_ID")
	@ForeignKey(name="FK_CONF_ITEM_PROX_ESTA")
	Map<Estado, ConfiguracionEstado> proximosEstados = new HashMap<Estado, ConfiguracionEstado>();
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_PROYECTO")
	@ForeignKey(name="FK_CONF_ITEM_PROYECTO")
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

	public boolean canChangeState(Estado estadoActual, Estado estadoNuevo) {
		
		if (proximosEstados.get(estadoActual) == null) {
			
			return false;
		}
		
		return proximosEstados.get(estadoActual).canChange(estadoNuevo);
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
		if (getClass() != obj.getClass())
			return false;
		ConfiguracionItem other = (ConfiguracionItem) obj;
		if (proximosEstados == null) {
			if (other.proximosEstados != null)
				return false;
		} else if (!proximosEstados.equals(other.proximosEstados))
			return false;
		if (tipoItem == null) {
			if (other.tipoItem != null)
				return false;
		} else if (!tipoItem.equals(other.tipoItem))
			return false;
		return true;
	}


}
