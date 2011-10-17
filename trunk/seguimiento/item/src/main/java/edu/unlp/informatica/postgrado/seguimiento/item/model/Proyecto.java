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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author  Victor.Martinez
 */
@Entity
@Table(name = "PROYECTO")
public class Proyecto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6774599877348543348L;

	@Id 
	@Column(name = "ID")
	@GeneratedValue(generator="PROYECTO_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="PROYECTO_ID_GEN", sequenceName="SEQ_PROYECTO_ID")
	Long id;
	
	@NotNull
	@Column(name = "NOMBRE", unique=true)
	String nombre;
	
	@ManyToMany(targetEntity=Integrante.class)
    @JoinTable(name="RESPONSABLES",
    		joinColumns=@JoinColumn(name="PROYECTO_ID"),
	        inverseJoinColumns=@JoinColumn(name="INTEGRANTE_ID"))
	List<Integrante> responsables;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_INTEGRANTE")
	Integrante lider;
	
    @OneToMany
    @JoinColumn(name="ID_PROYECTO")
	List<TipoItemProyecto> tipoItems;
	
	@ManyToMany(targetEntity=Integrante.class)
    @JoinTable(name="CONF_ITEM_PROYECTO",
    		joinColumns=@JoinColumn(name="PROYECTO_ID"),
	        inverseJoinColumns=@JoinColumn(name="CONF_ITEM_ID"))
	List<ConfiguracionItem> configuracionItems;
	
	@ManyToMany(targetEntity=Integrante.class)
    @JoinTable(name="ITEM",
    		joinColumns=@JoinColumn(name="PROYECTO_ID"),
	        inverseJoinColumns=@JoinColumn(name="ITEM_ID"))
	List<Item> items;

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

	public List<Integrante> getResponsables() {
		return responsables;
	}

	public void setResponsables(List<Integrante> responsables) {
		this.responsables = responsables;
	}

	public Integrante getLider() {
		return lider;
	}

	public void setLider(Integrante lider) {
		this.lider = lider;
	}

	public List<TipoItemProyecto> getTipoItems() {
		return tipoItems;
	}

	public void setTipoItems(List<TipoItemProyecto> tipoItems) {
		this.tipoItems = tipoItems;
	}

	public List<ConfiguracionItem> getConfiguracionItems() {
		return configuracionItems;
	}

	public void setConfiguracionItems(List<ConfiguracionItem> configuracionItems) {
		this.configuracionItems = configuracionItems;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((configuracionItems == null) ? 0 : configuracionItems
						.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((lider == null) ? 0 : lider.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((responsables == null) ? 0 : responsables.hashCode());
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
		if (getClass() != obj.getClass())
			return false;
		Proyecto other = (Proyecto) obj;
		if (configuracionItems == null) {
			if (other.configuracionItems != null)
				return false;
		} else if (!configuracionItems.equals(other.configuracionItems))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (lider == null) {
			if (other.lider != null)
				return false;
		} else if (!lider.equals(other.lider))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (responsables == null) {
			if (other.responsables != null)
				return false;
		} else if (!responsables.equals(other.responsables))
			return false;
		if (tipoItems == null) {
			if (other.tipoItems != null)
				return false;
		} else if (!tipoItems.equals(other.tipoItems))
			return false;
		return true;
	}
	
	
}
