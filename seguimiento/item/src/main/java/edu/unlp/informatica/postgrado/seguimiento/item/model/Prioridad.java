package edu.unlp.informatica.postgrado.seguimiento.item.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
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
	private Long id;
	
	@NotNull
	@Column(name = "NOMBRE", unique=true)
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
		if (getClass() != obj.getClass())
			return false;
		Prioridad other = (Prioridad) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	
}
