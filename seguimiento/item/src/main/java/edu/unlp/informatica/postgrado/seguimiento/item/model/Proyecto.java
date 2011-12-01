package edu.unlp.informatica.postgrado.seguimiento.item.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	@ManyToMany(targetEntity=Persona.class)
    @JoinTable(name="INTEGRANTES",
    		joinColumns=@JoinColumn(name="PROYECTO_ID"),
	        inverseJoinColumns=@JoinColumn(name="PERSONA_ID"))
	List<Persona> integrantes = new ArrayList<Persona>();
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_LIDER")
	Persona lider;
	
	@OneToMany
	@JoinTable(name="PROYECTO_CONF",
            joinColumns=@JoinColumn(name="PROYECTO_ID"),
            inverseJoinColumns=@JoinColumn(name="CONFIG_ITEM_ID"))
    @MapKeyJoinColumn(name="TIPO_ITEM_ID")
	Map<TipoItem, ConfiguracionItem> tipoItems = new HashMap<TipoItem, ConfiguracionItem>();

    @OneToMany(mappedBy="proyecto")
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
		this.integrantes = integrantes;
	}

	public Persona getLider() {
		return lider;
	}

	public void setLider(Persona lider) {
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
	
	public void addItem(Item item) {
		
		if (tipoItems.containsKey(item.getTipoItem())) {
			items.add(item);
		} else
			throw new IllegalArgumentException("El tipo de item no existe en el proyecto");
	}
	
	public boolean canChangeState(TipoItem tipoItem, Estado estado) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Set<TipoItem> getTipoItemList() {
		return this.getTipoItems().keySet();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((integrantes == null) ? 0 : integrantes.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
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
		if (getClass() != obj.getClass())
			return false;
		Proyecto other = (Proyecto) obj;
		if (integrantes == null) {
			if (other.integrantes != null)
				return false;
		} else if (!integrantes.equals(other.integrantes))
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
		if (tipoItems == null) {
			if (other.tipoItems != null)
				return false;
		} else if (!tipoItems.equals(other.tipoItems))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nombre;
	}

	
	
	
}
