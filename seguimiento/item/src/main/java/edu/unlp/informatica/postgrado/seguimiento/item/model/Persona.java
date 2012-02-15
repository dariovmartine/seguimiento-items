package edu.unlp.informatica.postgrado.seguimiento.item.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol;
import edu.unlp.informatica.postgrado.seguimiento.item.service.MappingOptions;

@Entity
@Table(name = "users")
public class Persona  implements Serializable, Numerable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7813991967157896619L;

	@Id 
	@Column(name = "ID")
	@GeneratedValue(generator="PERSONA_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="PERSONA_ID_GEN", sequenceName="SEQ_PERSONA_ID", allocationSize=1, initialValue=1)
	@MappingOptions
	private Long id;
			
	@NotNull
	@Column(name = "NOMBRE")
	@MappingOptions
	private String nombre;
		
	@NotNull
	@Size(max=50)
	@Column(name="username", unique=true, nullable=false)
	@MappingOptions
	String userName;
	
	@NotNull
	@Size(max=50)
	@Column(name="password")
	@MappingOptions
	String password;
	
	@NotNull	
	@Column(name="enabled")
	@MappingOptions
	boolean habilitado;
	
	@ElementCollection(targetClass=Rol.class) 
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "authorities",   joinColumns = @JoinColumn(name = "username", referencedColumnName="username"))
	@Column(name="authority", length=50 )
	@MappingOptions
	List<Rol> roles = new ArrayList<Rol>();

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
		Persona other = (Persona) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  nombre;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
	
	
}
