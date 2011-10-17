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
}
