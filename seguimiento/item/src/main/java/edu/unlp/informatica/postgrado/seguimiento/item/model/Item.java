package edu.unlp.informatica.postgrado.seguimiento.item.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.unlp.informatica.postgrado.seguimiento.item.validators.ValidUserName;

@Entity
@Table(name = "ITEM")
public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5039797189743799468L;

	@Id 
	@Column(name = "ID")
	@GeneratedValue(generator="ITEM_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ITEM_ID_GEN", sequenceName="SEQ_ITEM_ID")
	Long id;
	
	@ValidUserName
	@NotNull
	@Size(min = 3, max = 12)
	@Column(name = "nombre", unique=true)
	private String nombre;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_CONF_ITEM")
	private ConfiguracionItem configuracionItem;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_ESTADO")
	private Estado estado;	
		
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_INTEGRANTE")
	private Integrante responsable;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_PRIORIDAD")
	private Prioridad prioridad;

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

	public ConfiguracionItem getConfiguracionItem() {
		return configuracionItem;
	}

	public void setConfiguracionItem(ConfiguracionItem configuracionItem) {
		this.configuracionItem = configuracionItem;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Integrante getResponsable() {
		return responsable;
	}

	public void setResponsable(Integrante responsable) {
		this.responsable = responsable;
	}

	public Prioridad getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Prioridad prioridad) {
		this.prioridad = prioridad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((configuracionItem == null) ? 0 : configuracionItem
						.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((prioridad == null) ? 0 : prioridad.hashCode());
		result = prime * result
				+ ((responsable == null) ? 0 : responsable.hashCode());
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
		Item other = (Item) obj;
		if (configuracionItem == null) {
			if (other.configuracionItem != null)
				return false;
		} else if (!configuracionItem.equals(other.configuracionItem))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (prioridad == null) {
			if (other.prioridad != null)
				return false;
		} else if (!prioridad.equals(other.prioridad))
			return false;
		if (responsable == null) {
			if (other.responsable != null)
				return false;
		} else if (!responsable.equals(other.responsable))
			return false;
		return true;
	}
	
	
}
