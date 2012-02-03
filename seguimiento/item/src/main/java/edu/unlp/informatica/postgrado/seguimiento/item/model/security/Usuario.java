package edu.unlp.informatica.postgrado.seguimiento.item.model.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Numerable;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;

@Entity
@Table(name = "users")
public class Usuario implements Serializable, Numerable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 349512660083161617L;

	@Column(name = "ID")
	@GeneratedValue(generator="USUARIO_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="USUARIO_ID_GEN", sequenceName="SEQ_USUARIO_ID", allocationSize=1, initialValue=1)
	Long id;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "ID_PERSONA")
	Persona persona;
	
	@Id 
	@NotNull
	@Size(max=50)
	@Column(name="username")
	String nombre;
	
	@NotNull
	@Size(max=50)
	@Column(name="password")
	String password;
	
	@NotNull	
	@Column(name="enabled")
	boolean habilitado;
	
	@ElementCollection(targetClass=Rol.class) 
//	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)	
//	@JoinTable(name="authorities",
//            joinColumns=@JoinColumn(name="username "),
//            inverseJoinColumns=@JoinColumn(name="username"))
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "authorities",   joinColumns = @JoinColumn(name = "username"))
	@Column(name="authority", length=50 )
	List<Rol> roles = new ArrayList<Rol>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
