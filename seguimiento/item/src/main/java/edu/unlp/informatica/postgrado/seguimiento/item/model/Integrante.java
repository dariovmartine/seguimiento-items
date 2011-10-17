package edu.unlp.informatica.postgrado.seguimiento.item.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "INTEGRANTE")
public class Integrante extends Persona  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2619541922437067909L;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_PROYECTO")
	private Proyecto proyecto;

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
}
