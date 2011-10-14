package edu.unlp.informatica.postgrado.seguimiento.item.model;

import java.io.Serializable;

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
	@Column(name = "NAME", unique=true)
	private String name;

	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_ESTADO")
	private Estado estado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
}
