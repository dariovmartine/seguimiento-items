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
@Table(name = "TIPO_ITEM")
public class TipoItem  implements Serializable, Numerable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1153717887563098454L;

	@Id 
	@Column(name = "ID")
	@GeneratedValue(generator="TIPO_ITEM_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="TIPO_ITEM_ID_GEN", sequenceName="SEQ_TIPO_ITEM_ID", allocationSize=1, initialValue=1)
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (! (obj instanceof TipoItem))
			return false;
		TipoItem other = (TipoItem) obj;
		if (nombre == null) {
			if (other.getNombre() != null)
				return false;
		} else if (!nombre.equals(other.getNombre()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nombre ;
	}	
}
