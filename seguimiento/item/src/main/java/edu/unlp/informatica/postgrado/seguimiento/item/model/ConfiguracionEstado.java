package edu.unlp.informatica.postgrado.seguimiento.item.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author  Victor.Martinez
 */
@Entity
@Table(name = "CONFIG_ESTADO")
public class ConfiguracionEstado implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4813852462726118902L;

	@Id 
	@Column(name = "ID")
	@GeneratedValue(generator="CONFIG_ESTADO_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="CONFIG_ESTADO_ID_GEN", sequenceName="SEQ_CONFIG_ESTADO_ID")
	private Long id;

	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_ESTADO")
	private Estado estado;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_CONFIGURACION_ITEM")
	private ConfiguracionItem configuracionItem;
	
	@ManyToMany(targetEntity=Estado.class)
    @JoinTable(name="PROXIMO_ESTADO",
    		joinColumns=@JoinColumn(name="CONF_ITEM_ID"),
	        inverseJoinColumns=@JoinColumn(name="ESTADO_ID"))
	private List<Estado> proximosEstados = new ArrayList<Estado>();
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Estado> getProximosEstados() {
		return proximosEstados;
	}

	public void setProximosEstados(List<Estado> proximosEstados) {
		this.proximosEstados = proximosEstados;
	}

	public boolean canChange(Estado estadoNuevo) {
		
		if (proximosEstados == null) {
			
			return false;
		}
		
		return proximosEstados.contains(estadoNuevo);
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public ConfiguracionItem getConfiguracionItem() {
		return configuracionItem;
	}

	public void setConfiguracionItem(ConfiguracionItem configuracionItem) {
		this.configuracionItem = configuracionItem;
	}
	
	@Override
	public String toString() {
		return proximosEstados.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result
				+ ((proximosEstados == null) ? 0 : proximosEstados.hashCode());
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
		ConfiguracionEstado other = (ConfiguracionEstado) obj;
	
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (proximosEstados == null) {
			if (other.proximosEstados != null)
				return false;
		} else if (!proximosEstados.equals(other.proximosEstados))
			return false;
		return true;
	}


}
