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
}
