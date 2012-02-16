package edu.unlp.informatica.postgrado.seguimiento.item.model;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import edu.unlp.informatica.postgrado.seguimiento.item.service.MappingOptions;

@Entity
@Access(AccessType.FIELD)
@Table(name = "PRIORIDAD")
public class Prioridad  implements Serializable, Numerable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2855492932078134356L;

	@Id 
	@Column(name = "ID")
	@GeneratedValue(generator="PRIORIDAD_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="PRIORIDAD_ID_GEN", sequenceName="SEQ_PRIORIDAD_ID", allocationSize=1, initialValue=1)
	@MappingOptions
	private Long id;
	
	@NotNull
	@Column(name = "NOMBRE", unique=true)
	@MappingOptions
	private String nombre;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (! (obj instanceof Prioridad))
			return false;
		Prioridad other = (Prioridad) obj;
		if (nombre == null) {
			if (other.getNombre() != null)
				return false;
		} else if (!nombre.equals(other.getNombre()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	
}
