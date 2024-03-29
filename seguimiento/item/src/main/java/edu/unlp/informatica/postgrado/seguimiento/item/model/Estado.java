package edu.unlp.informatica.postgrado.seguimiento.item.model;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import edu.unlp.informatica.postgrado.seguimiento.item.service.MappingOptions;
import edu.unlp.informatica.postgrado.seguimiento.item.validators.ValidUserName;

@Entity
@Access(AccessType.FIELD)
@Table(name = "ESTADO")
public class Estado implements Serializable, Numerable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5351658617930139784L;

	@Id 
	@Column(name = "ID")
	@GeneratedValue(generator="ESTADO_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ESTADO_ID_GEN", sequenceName="SEQ_ESTADO_ID", allocationSize=1, initialValue=1)
	@MappingOptions
	Long id;
	
	@ValidUserName
	@NotNull
	@Column(name = "nombre", unique=true)
	@MappingOptions
	private String nombre;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@MappingOptions
	private TipoEstado tipoEstado;
		
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

	public TipoEstado getTipoEstado() {
		return tipoEstado;
	}

	public void setTipoEstado(TipoEstado tipoEstado) {
		this.tipoEstado = tipoEstado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
//		result = prime * result
//				+ ((tipoEstado == null) ? 0 : tipoEstado.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Estado))
			return false;
		Estado other = (Estado) obj;
		if (nombre == null) {
			if (other.getNombre() != null)
				return false;
		} else if (!nombre.equals(other.getNombre()))
			return false;
		if (tipoEstado != other.getTipoEstado())
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return nombre;
	}
}
