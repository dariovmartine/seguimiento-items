package edu.unlp.informatica.postgrado.seguimiento.item.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import edu.unlp.informatica.postgrado.seguimiento.item.validators.ValidUserName;

@Entity
@Table(name = "ESTADO")
public class Estado implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5351658617930139784L;

	@Id 
	@Column(name = "ID")
	@GeneratedValue(generator="EXAMPLE_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="EXAMPLE_ID_GEN", sequenceName="SEQ_ESTADO_ID")
	Long id;
	
	@ValidUserName
	@Size(min = 3, max = 12, message = "Must be between 3 and 12 chars")
	@Column(name = "NAME")
	private String name;
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Estado [name=" + name + "]";
	}
	
	
}
