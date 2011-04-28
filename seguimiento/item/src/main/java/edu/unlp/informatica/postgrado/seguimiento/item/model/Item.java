package edu.unlp.informatica.postgrado.seguimiento.item.model;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

import edu.unlp.informatica.postgrado.seguimiento.item.validators.ValidUserName;

// @ManagedBean    Lo mandamos al faces-config.xml
// @RequestScoped   Lo mandamos al faces-config.xml
@Entity(name= "ITEM" )
public class Item {

	@ValidUserName
	@Size(min = 3, max = 12, message = "Must be between 3 and 12 chars")
	private String name;

	private String state;
	
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
