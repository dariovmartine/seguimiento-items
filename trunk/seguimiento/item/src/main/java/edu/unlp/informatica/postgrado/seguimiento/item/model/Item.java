package edu.unlp.informatica.postgrado.seguimiento.item.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.unlp.informatica.postgrado.seguimiento.item.validators.ValidUserName;

// @ManagedBean    Lo mandamos al faces-config.xml
// @RequestScoped   Lo mandamos al faces-config.xml
@Entity
@Table(name = "ITEM")
public class Item {

	@Id 
	@Column(name = "ID", unique = true, nullable = false, insertable = true, updatable = true)
	@GeneratedValue(generator="EXAMPLE_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="EXAMPLE_ID_GEN", sequenceName="SEQ_PERSON_ID")
	Long id;
	
	@ValidUserName
	@Size(min = 3, max = 12, message = "Must be between 3 and 12 chars")
	@Column(name = "NAME")
	private String name;

	@NotNull
	@Column(name = "STATE")
	private String state;

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

	public void setState(String state) {
		this.state = state;
	}
	
	public String getState() {
		return state;
	}
	

}
